package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.dto.SaleItemCreateRequest;
import com.store.conveniencestore.dto.SaleOrderCreateRequest;
import com.store.conveniencestore.dto.SaleOrderResponse;
import com.store.conveniencestore.entity.Product;
import com.store.conveniencestore.entity.SaleItem;
import com.store.conveniencestore.entity.SaleOrder;
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

    public SaleOrderServiceImpl(
            SaleOrderMapper saleOrderMapper,
            SaleItemMapper saleItemMapper,
            ProductMapper productMapper) {

        this.saleOrderMapper = saleOrderMapper;
        this.saleItemMapper = saleItemMapper;
        this.productMapper = productMapper;
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
            savedItems.add(saleItem);
        }

        /*
         * 第五步：返回完整的销售订单结果。
         */
        return new SaleOrderResponse(
                saleOrder.getId(),
                saleOrder.getSaleTime(),
                savedItems
        );
    }

    @Override
    public void update(SaleOrder saleOrder) {
        saleOrderMapper.update(saleOrder);
    }

    @Override
    public void delete(Integer id) {
        saleOrderMapper.delete(id);
    }
}