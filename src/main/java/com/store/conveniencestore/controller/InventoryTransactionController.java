package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.service.InventoryTransactionService;
import jakarta.validation.constraints.Positive;
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
    public ApiResponse<List<InventoryTransaction>>
    findAll() {

        List<InventoryTransaction> transactions =
                inventoryTransactionService.findAll();

        return ApiResponse.success(transactions);
    }

    /**
     * 查询指定商品的库存流水。
     *
     * GET /inventory-transactions/product/1
     */
    @GetMapping("/product/{productId}")
    public ApiResponse<List<InventoryTransaction>>
    findByProductId(
            @PathVariable @Positive(message = "商品编号必须大于0") Integer productId) {

        List<InventoryTransaction> transactions =
                inventoryTransactionService
                        .findByProductId(productId);

        return ApiResponse.success(transactions);
    }
}