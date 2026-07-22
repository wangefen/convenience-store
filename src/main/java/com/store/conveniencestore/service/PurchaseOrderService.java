package com.store.conveniencestore.service;

import com.store.conveniencestore.dto.PurchaseOrderCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderResponse;
import com.store.conveniencestore.entity.PurchaseOrder;

import java.util.List;

/**
 * 采购订单业务层接口。
 *
 * 规定采购订单模块提供哪些业务功能。
 */
public interface PurchaseOrderService {

    /**
     * 查询全部采购订单。
     */
    List<PurchaseOrder> findAll();

    /**
     * 根据 id 查询采购订单。
     */
    PurchaseOrder findById(Integer id);

    /**
     * 创建采购订单，并同时创建该订单下的多条采购明细。
     */
    PurchaseOrderResponse createOrder(PurchaseOrderCreateRequest request);

    /**
     * 修改采购订单。
     */
    void update(PurchaseOrder purchaseOrder);

    /**
     * 根据 id 删除采购订单。
     */
    void delete(Integer id);
}