package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.PurchaseItem;

import java.util.List;

/**
 * 采购明细业务层接口。
 */
public interface PurchaseItemService {

    /**
     * 查询全部采购明细。
     */
    List<PurchaseItem> findAll();

    /**
     * 根据明细 id 查询。
     */
    PurchaseItem findById(Integer id);

    /**
     * 根据采购订单 id 查询该订单的全部明细。
     */
    List<PurchaseItem> findByPurchaseOrderId(Integer purchaseOrderId);

    /**
     * 新增一条采购明细。
     */
    void insert(PurchaseItem purchaseItem);

    /**
     * 修改采购明细。
     */
    void update(PurchaseItem purchaseItem);

    /**
     * 删除采购明细。
     */
    void delete(Integer id);
}