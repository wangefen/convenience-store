package com.store.conveniencestore.controller;

import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.service.InventoryTransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 库存流水控制层。
 */
@RestController
@RequestMapping("/inventory-transactions")
public class InventoryTransactionController {

    private final InventoryTransactionService
            inventoryTransactionService;

    public InventoryTransactionController(
            InventoryTransactionService
                    inventoryTransactionService) {

        this.inventoryTransactionService =
                inventoryTransactionService;
    }

    /**
     * 查询全部库存流水。
     *
     * GET /inventory-transactions
     */
    @GetMapping
    public List<InventoryTransaction> findAll() {
        return inventoryTransactionService.findAll();
    }

    /**
     * 查询指定商品的库存流水。
     *
     * GET /inventory-transactions/product/1
     */
    @GetMapping("/product/{productId}")
    public List<InventoryTransaction> findByProductId(
            @PathVariable Integer productId) {

        return inventoryTransactionService
                .findByProductId(productId);
    }
}