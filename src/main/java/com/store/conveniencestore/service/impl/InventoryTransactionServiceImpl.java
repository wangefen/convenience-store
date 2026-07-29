package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.mapper.InventoryTransactionMapper;
import com.store.conveniencestore.service.InventoryTransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存流水业务层实现类。
 */
@Service
public class InventoryTransactionServiceImpl
        implements InventoryTransactionService {

    private final InventoryTransactionMapper
            inventoryTransactionMapper;

    public InventoryTransactionServiceImpl(
            InventoryTransactionMapper
                    inventoryTransactionMapper) {

        this.inventoryTransactionMapper =
                inventoryTransactionMapper;
    }

    @Override
    public List<InventoryTransaction> findAll() {
        return inventoryTransactionMapper.findAll();
    }

    @Override
    public List<InventoryTransaction> findByProductId(
            Integer productId) {

        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException(
                    "商品编号必须大于0"
            );
        }

        return inventoryTransactionMapper
                .findByProductId(productId);
    }
}