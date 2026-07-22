package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.dto.PurchaseItemCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderResponse;
import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.entity.PurchaseOrder;
import com.store.conveniencestore.mapper.PurchaseItemMapper;
import com.store.conveniencestore.mapper.PurchaseOrderMapper;
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

    //因为这里用到了mapper,是spring提供的管理组件，所以要依赖注入，自己写的类就不用直接new就行
    public PurchaseOrderServiceImpl(PurchaseOrderMapper purchaseOrderMapper,
                                    PurchaseItemMapper purchaseItemMapper){
        this.purchaseOrderMapper = purchaseOrderMapper;
        this.purchaseItemMapper = purchaseItemMapper;
    }


    @Override
    public List<PurchaseOrder> findAll(){
        return purchaseOrderMapper.findAll();
    }

    @Override
    public PurchaseOrder findById(Integer id){
        return purchaseOrderMapper.findById(id);
    }

    @Transactional //@Transactional 用来保证一个业务方法中的多次数据库操作要么全部成功，要么发生异常时全部回滚。
    @Override
    public PurchaseOrderResponse createOrder(PurchaseOrderCreateRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("采购订单数据不能为空");
        }

        if (request.supplierId() == null) {
            throw new IllegalArgumentException("供应商编号不能为空");
        }
        if (request.items() == null
                || request.items().isEmpty()) {
            throw new IllegalArgumentException(
                    "采购订单至少包含一条明细"
            );
        }

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setSupplierId(request.supplierId());
        purchaseOrder.setPurchaseTime(LocalDateTime.now());

        purchaseOrderMapper.insert(purchaseOrder);

        List<PurchaseItem> savedItems = new ArrayList<>();

        for (PurchaseItemCreateRequest itemRequest : request.items()){
            if (itemRequest == null){
                throw new IllegalArgumentException(
                        "采购明细不能为空"
                );
            }
            if (itemRequest.productId() == null){
                throw new IllegalArgumentException(
                        "商品编号不能为空"
                );
            }
            if (itemRequest.quantity() <= 0){
                throw new IllegalArgumentException(
                        "采购量必须大于0"
                );
            }
            if (itemRequest.purchasePrice() == null ){

                throw new IllegalArgumentException(
                        "采购价格必须大于0"
                );
            }

            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setPurchaseOrderId(purchaseOrder.getId());
            purchaseItem.setProductId(itemRequest.productId());
            purchaseItem.setQuantity(itemRequest.quantity());
            purchaseItem.setPurchasePrice(itemRequest.purchasePrice());

            purchaseItemMapper.insert(purchaseItem);
            savedItems.add(purchaseItem);
        }

        return new PurchaseOrderResponse(
                purchaseOrder.getId(),
                purchaseOrder.getSupplierId(),
                purchaseOrder.getPurchaseTime(),
                savedItems
        );
    }

    /**
     * 修改采购订单。
     */
    @Override
    public void update(PurchaseOrder purchaseOrder) {
        purchaseOrderMapper.update(purchaseOrder);
    }

    /**
     * 删除采购订单。
     */
    @Override
    public void delete(Integer id) {
        purchaseOrderMapper.delete(id);
    }
}