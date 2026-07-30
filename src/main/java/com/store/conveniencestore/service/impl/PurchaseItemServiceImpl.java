package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.mapper.PurchaseItemMapper;
import com.store.conveniencestore.service.PurchaseItemService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 采购明细业务层实现类。
 */
@Service
public class PurchaseItemServiceImpl implements PurchaseItemService {

    private final PurchaseItemMapper purchaseItemMapper;

    /**
     * 构造器注入 PurchaseItemMapper。
     */
    public PurchaseItemServiceImpl(
            PurchaseItemMapper purchaseItemMapper) {
        this.purchaseItemMapper = purchaseItemMapper;
    }

    /**
     * 查询全部采购明细。
     */
    @Override
    public List<PurchaseItem> findAll() {
        return purchaseItemMapper.findAll();
    }

    /**
     * 根据明细 id 查询。
     */
    @Override
    public PurchaseItem findById(Integer id) {
        return purchaseItemMapper.findById(id);
    }

    /**
     * 查询某张采购订单的全部明细。
     */
    @Override
    public List<PurchaseItem> findByPurchaseOrderId(
            Integer purchaseOrderId) {

        return purchaseItemMapper
                .findByPurchaseOrderId(purchaseOrderId);
    }

}