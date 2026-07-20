package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.PurchaseOrder;
import com.store.conveniencestore.mapper.PurchaseOrderMapper;
import com.store.conveniencestore.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 采购订单业务层实现类。
 */
@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderMapper purchaseOrderMapper;

    /**
     * 构造器注入。
     *
     * Spring 会自动把 PurchaseOrderMapper 对象传进来。
     */
    public PurchaseOrderServiceImpl(
            PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    /**
     * 查询全部采购订单。
     */
    @Override
    public List<PurchaseOrder> findAll() {
        return purchaseOrderMapper.findAll();
    }

    /**
     * 根据 id 查询采购订单。
     */
    @Override
    public PurchaseOrder findById(Integer id) {
        return purchaseOrderMapper.findById(id);
    }

    /**
     * 新增采购订单。
     */
    @Override
    public void insert(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getPurchaseTime() == null){
            purchaseOrder.setPurchaseTime(LocalDateTime.now());
        }
        purchaseOrderMapper.insert(purchaseOrder);
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