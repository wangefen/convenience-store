package com.store.conveniencestore.service;

import com.store.conveniencestore.entity.InventoryTransaction;

import java.util.List;

/**
 * 库存流水业务层接口。
 */
public interface InventoryTransactionService {

    /**
     * 查询全部库存流水。
     */
    List<InventoryTransaction> findAll();

    /**
     * 查询指定商品的库存流水。
     */
    List<InventoryTransaction> findByProductId(
            Integer productId
    );
}