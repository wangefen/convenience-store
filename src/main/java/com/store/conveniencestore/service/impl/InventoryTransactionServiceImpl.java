package com.store.conveniencestore.service.impl;

import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.exception.ResourceNotFoundException;
import com.store.conveniencestore.mapper.InventoryTransactionMapper;
import com.store.conveniencestore.mapper.ProductMapper;
import com.store.conveniencestore.service.InventoryTransactionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存流水业务层实现类。
 */
@Service
public class InventoryTransactionServiceImpl
        implements InventoryTransactionService {

    private final InventoryTransactionMapper inventoryTransactionMapper;
    private final ProductMapper productMapper;

    public InventoryTransactionServiceImpl(
            InventoryTransactionMapper
                    inventoryTransactionMapper, ProductMapper productMapper) {

        this.inventoryTransactionMapper =
                inventoryTransactionMapper;
        this.productMapper = productMapper;
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

        if (productMapper.findById(productId) == null) {
            throw new ResourceNotFoundException(
                    "商品不存在，商品编号：" + productId
            );
        }

        return inventoryTransactionMapper
                .findByProductId(productId);
    }
}