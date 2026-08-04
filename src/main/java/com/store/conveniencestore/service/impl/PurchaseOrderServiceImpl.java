package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.dto.PurchaseItemCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderResponse;
import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.entity.PurchaseOrder;
import com.store.conveniencestore.exception.BusinessConflictException;
import com.store.conveniencestore.exception.ResourceNotFoundException;
import com.store.conveniencestore.mapper.*;
import com.store.conveniencestore.service.PurchaseOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService{
    private final PurchaseOrderMapper purchaseOrderMapper;
    private final PurchaseItemMapper purchaseItemMapper;
    private final ProductMapper productMapper;
    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final SupplierMapper supplierMapper;

    //因为这里用到了mapper,是spring提供的管理组件，所以要依赖注入，自己写的类就不用直接new就行
    public PurchaseOrderServiceImpl(PurchaseOrderMapper purchaseOrderMapper,
                                    PurchaseItemMapper purchaseItemMapper,
                                    ProductMapper productMapper,
                                    InventoryTransactionMapper inventoryTransactionMapper, SupplierMapper supplierMapper){
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseItemMapper = purchaseItemMapper;
        this.productMapper = productMapper;
        this.inventoryTransactionMapper = inventoryTransactionMapper;
        this.supplierMapper = supplierMapper;
    }


    @Override
    public List<PurchaseOrder> findAll(){
        return purchaseOrderMapper.findAll();
    }

    @Override
    public PurchaseOrder findById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "采购订单编号必须大于0"
            );
        }

        PurchaseOrder purchaseOrder =
                purchaseOrderMapper.findById(id);

        if (purchaseOrder == null) {
            throw new ResourceNotFoundException(
                    "采购订单不存在，订单编号：" + id
            );
        }

        return purchaseOrder;
    }

    @Transactional //@Transactional 用来保证一个业务方法中的多次数据库操作要么全部成功，要么发生异常时全部回滚。
    @Override
    public PurchaseOrderResponse createOrder(PurchaseOrderCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("采购订单数据不能为空");
        }

        /*
         * DTO已经检查supplierId非空且大于0。
         * Service负责检查供应商在数据库中是否真实存在。
         */

        if (supplierMapper.findById(request.supplierId()) == null) {

            throw new ResourceNotFoundException(
                    "供应商不存在，供应商编号："
                            + request.supplierId()
            );
        }



        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierId(request.supplierId());
        purchaseOrder.setPurchaseTime(LocalDateTime.now());
        purchaseOrder.setStatus("COMPLETED");

        purchaseOrderMapper.insert(purchaseOrder);

        List<PurchaseItem> savedItems = new ArrayList<>();

        for (PurchaseItemCreateRequest itemRequest : request.items()){
            /*
             * DTO已经检查：
             * 1. 明细不能为null
             * 2. productId必须大于0
             * 3. quantity必须大于0
             * 4. purchasePrice必须大于0
             *
             * Service只检查商品是否真实存在。
             */

            if (productMapper.findById(
                    itemRequest.productId()) == null) {

                throw new ResourceNotFoundException(
                        "商品不存在，商品编号："
                                + itemRequest.productId()
                );
            }

            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setPurchaseOrderId(purchaseOrder.getId());
            purchaseItem.setProductId(itemRequest.productId());
            purchaseItem.setQuantity(itemRequest.quantity());
            purchaseItem.setPurchasePrice(itemRequest.purchasePrice());

            purchaseItemMapper.insert(purchaseItem);

            int affectedRows = productMapper.increaseStock(
                    purchaseItem.getProductId(),
                    purchaseItem.getQuantity()
            );

            if (affectedRows == 0){
                throw new BusinessConflictException(
                        "商品状态已经发生变化，采购入库失败，商品编号："+itemRequest.productId()
                );
            }

            /*
             * 记录采购入库流水。
             */
            InventoryTransaction inventoryTransaction =
                    new InventoryTransaction();

            inventoryTransaction.setProductId(
                    purchaseItem.getProductId()
            );
            inventoryTransaction.setChangeQuantity(
                    purchaseItem.getQuantity()
            );
            inventoryTransaction.setBusinessType("PURCHASE");
            inventoryTransaction.setBusinessId(
                    purchaseOrder.getId()
            );
            inventoryTransaction.setCreateTime(
                    LocalDateTime.now()
            );

            inventoryTransactionMapper.insert(
                    inventoryTransaction
            );

            savedItems.add(purchaseItem);
        }

        return new PurchaseOrderResponse(
                purchaseOrder.getId(),
                purchaseOrder.getSupplierId(),
                purchaseOrder.getPurchaseTime(),
                purchaseOrder.getStatus(),
                savedItems
        );
    }

    /**
     * 取消采购订单。
     *
     * 订单状态、库存和冲销流水必须在同一个事务中处理。
     */
    @Transactional
    @Override
    public PurchaseOrder cancel(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "采购订单编号必须大于0"
            );
        }

        PurchaseOrder purchaseOrder =
                purchaseOrderMapper.findById(id);

        if (purchaseOrder == null) {
            throw new ResourceNotFoundException(
                    "采购订单不存在，订单编号：" + id
            );
        }

        if ("CANCELLED".equals(purchaseOrder.getStatus())) {
            throw new BusinessConflictException(
                    "采购订单已经取消，不能重复取消"
            );
        }

        /*
         * 原子修改订单状态。
         *
         * 并发取消时，只有一个请求能将
         * COMPLETED 修改为 CANCELLED。
         */
        int affectedRows =
                purchaseOrderMapper.cancelIfCompleted(id);

        if (affectedRows == 0) {
            throw new BusinessConflictException(
                    "采购订单状态已经发生变化，取消失败"
            );
        }

        List<PurchaseItem> purchaseItems =
                purchaseItemMapper.findByPurchaseOrderId(id);

        if (purchaseItems == null || purchaseItems.isEmpty()) {
            throw new IllegalStateException(
                    "采购订单没有对应的采购明细"
            );
        }

        for (PurchaseItem purchaseItem : purchaseItems) {

            /*
             * 采购时增加了库存，
             * 取消采购时需要把库存减回来。
             */
            int stockAffectedRows =
                    productMapper.decreaseStock(
                            purchaseItem.getProductId(),
                            purchaseItem.getQuantity()
                    );

            if (stockAffectedRows == 0) {
                throw new IllegalStateException(
                        "商品不存在或当前库存不足，无法取消采购订单，商品编号："
                                + purchaseItem.getProductId()
                );
            }

            /*
             * 记录采购取消产生的反向流水。
             *
             * 原采购为 +10，
             * 取消采购则记录 -10。
             */
            InventoryTransaction transaction =
                    new InventoryTransaction();

            transaction.setProductId(
                    purchaseItem.getProductId()
            );
            transaction.setChangeQuantity(
                    -purchaseItem.getQuantity()
            );
            transaction.setBusinessType(
                    "PURCHASE_CANCEL"
            );
            transaction.setBusinessId(id);
            transaction.setCreateTime(
                    LocalDateTime.now()
            );

            inventoryTransactionMapper.insert(
                    transaction
            );
        }

        return purchaseOrderMapper.findById(id);
    }
}