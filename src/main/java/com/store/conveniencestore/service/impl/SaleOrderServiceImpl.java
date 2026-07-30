package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.dto.SaleItemCreateRequest;
import com.store.conveniencestore.dto.SaleOrderCreateRequest;
import com.store.conveniencestore.dto.SaleOrderResponse;
import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.entity.SaleItem;
import com.store.conveniencestore.entity.SaleOrder;
import com.store.conveniencestore.mapper.InventoryTransactionMapper;
import com.store.conveniencestore.mapper.ProductMapper;
import com.store.conveniencestore.mapper.SaleItemMapper;
import com.store.conveniencestore.mapper.SaleOrderMapper;
import com.store.conveniencestore.service.SaleOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 销售订单业务层实现类。
 */
@Service
public class SaleOrderServiceImpl implements SaleOrderService {

    private final SaleOrderMapper saleOrderMapper;
    private final SaleItemMapper saleItemMapper;
    private final ProductMapper productMapper;
    private final InventoryTransactionMapper inventoryTransactionMapper;

    public SaleOrderServiceImpl(
            SaleOrderMapper saleOrderMapper,
            SaleItemMapper saleItemMapper,
            ProductMapper productMapper,
            InventoryTransactionMapper inventoryTransactionMapper) {

        this.saleOrderMapper = saleOrderMapper;
        this.saleItemMapper = saleItemMapper;
        this.productMapper = productMapper;
        this.inventoryTransactionMapper = inventoryTransactionMapper;
    }

    @Override
    public List<SaleOrder> findAll() {
        return saleOrderMapper.findAll();
    }

    @Override
    public SaleOrder findById(Integer id) {
        return saleOrderMapper.findById(id);
    }

    /**
     * 创建销售订单和对应的多条销售明细。
     */
    @Transactional
    @Override
    public SaleOrderResponse createOrder(
            SaleOrderCreateRequest request) {

        /*
         * 第一步：检查整个请求。
         */
        if (request == null) {
            throw new IllegalArgumentException(
                    "销售订单数据不能为空"
            );
        }

        if (request.items() == null
                || request.items().isEmpty()) {

            throw new IllegalArgumentException(
                    "销售订单至少包含一条明细"
            );
        }

        /*
         * 第二步：创建销售订单。
         */
        SaleOrder saleOrder = new SaleOrder();
        saleOrder.setSaleTime(LocalDateTime.now());
        saleOrder.setStatus("COMPLETED");

        /*
         * insert 后，数据库生成的自增 id
         * 会写入 saleOrder.id。
         */
        saleOrderMapper.insert(saleOrder);

        List<SaleItem> savedItems = new ArrayList<>();

        /*
         * 第三步：依次处理每条销售明细。
         */
        for (SaleItemCreateRequest itemRequest
                : request.items()) {

            if (itemRequest == null) {
                throw new IllegalArgumentException(
                        "销售明细不能为空"
                );
            }

            if (itemRequest.productId() == null) {
                throw new IllegalArgumentException(
                        "商品编号不能为空"
                );
            }

            if (itemRequest.quantity() == null
                    || itemRequest.quantity() <= 0) {

                throw new IllegalArgumentException(
                        "销售数量必须大于0"
                );
            }

            /*
             * 根据前端传入的商品编号查询商品。
             */
            Product product = productMapper.findById(
                    itemRequest.productId()
            );

            if (product == null) {
                throw new IllegalArgumentException(
                        "商品不存在，商品编号："
                                + itemRequest.productId()
                );
            }

            if (product.getSalePrice() == null
                    || product.getSalePrice().signum() <= 0) {

                throw new IllegalArgumentException(
                        "商品销售价格不正确，商品编号："
                                + product.getId()
                );
            }

            /*
             * 原子扣减库存。
             *
             * SQL 会同时检查库存是否充足，
             * 因此不会出现负库存。
             */
            int affectedRows = productMapper.decreaseStock(
                    product.getId(),
                    itemRequest.quantity()
            );

            /*
             * 商品前面已经确认存在，
             * 所以这里返回0，通常表示库存不足。
             */
            if (affectedRows == 0) {
                throw new IllegalArgumentException(
                        "商品库存不足，商品编号："
                                + product.getId()
                );
            }


            /*
             * 第四步：创建销售明细实体对象。
             */
            SaleItem saleItem = new SaleItem();

            saleItem.setSaleOrderId(saleOrder.getId());
            saleItem.setProductId(product.getId());
            saleItem.setQuantity(itemRequest.quantity());

            /*
             * 销售价格使用数据库中商品的价格，
             * 不直接相信前端提交的数据。
             */
            saleItem.setSalePrice(product.getSalePrice());

            saleItemMapper.insert(saleItem);
            /*
             * 记录销售出库流水。
             *
             * 销售会减少库存，所以使用负数。
             */
            InventoryTransaction inventoryTransaction =
                    new InventoryTransaction();

            inventoryTransaction.setProductId(
                    saleItem.getProductId()
            );
            inventoryTransaction.setChangeQuantity(
                    -saleItem.getQuantity()
            );
            inventoryTransaction.setBusinessType("SALE");
            inventoryTransaction.setBusinessId(
                    saleOrder.getId()
            );
            inventoryTransaction.setCreateTime(
                    LocalDateTime.now()
            );

            inventoryTransactionMapper.insert(
                    inventoryTransaction
            );
            savedItems.add(saleItem);
        }

        /*
         * 第五步：返回完整的销售订单结果。
         */
        return new SaleOrderResponse(
                saleOrder.getId(),
                saleOrder.getSaleTime(),
                saleOrder.getStatus(),
                savedItems
        );
    }

    /**
     * 取消销售订单。
     *
     * 状态、库存和库存流水必须处于同一个事务中。
     */
    @Transactional
    @Override
    public SaleOrder cancel(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "销售订单编号必须大于0"
            );
        }

        SaleOrder saleOrder =
                saleOrderMapper.findById(id);

        if (saleOrder == null) {
            throw new IllegalArgumentException(
                    "销售订单不存在，订单编号：" + id
            );
        }

        if ("CANCELLED".equals(saleOrder.getStatus())) {
            throw new IllegalArgumentException(
                    "销售订单已经取消，不能重复取消"
            );
        }

        /*
         * 原子修改状态。
         * 并发取消时只有一个请求能够成功。
         */
        int affectedRows =
                saleOrderMapper.cancelIfCompleted(id);

        if (affectedRows == 0) {
            throw new IllegalStateException(
                    "销售订单状态已经发生变化，取消失败"
            );
        }

        List<SaleItem> saleItems =
                saleItemMapper.findBySaleOrderId(id);

        if (saleItems == null || saleItems.isEmpty()) {
            throw new IllegalStateException(
                    "销售订单没有对应的销售明细"
            );
        }

        for (SaleItem saleItem : saleItems) {

            /*
             * 销售时扣减了库存，
             * 取消销售时把库存加回来。
             */
            int stockAffectedRows =
                    productMapper.increaseStock(
                            saleItem.getProductId(),
                            saleItem.getQuantity()
                    );

            if (stockAffectedRows == 0) {
                throw new IllegalStateException(
                        "商品不存在，无法恢复库存，商品编号："
                                + saleItem.getProductId()
                );
            }

            /*
             * 原销售流水为负数，例如 -3；
             * 取消销售记录正数 +3。
             */
            InventoryTransaction transaction =
                    new InventoryTransaction();

            transaction.setProductId(
                    saleItem.getProductId()
            );
            transaction.setChangeQuantity(
                    saleItem.getQuantity()
            );
            transaction.setBusinessType(
                    "SALE_CANCEL"
            );
            transaction.setBusinessId(id);
            transaction.setCreateTime(
                    LocalDateTime.now()
            );

            inventoryTransactionMapper.insert(
                    transaction
            );
        }

        return saleOrderMapper.findById(id);
    }
}