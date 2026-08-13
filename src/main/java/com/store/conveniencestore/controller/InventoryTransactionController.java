package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.entity.InventoryTransaction;
import com.store.conveniencestore.service.InventoryTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

/**
 * 库存流水控制层。
 */
@Tag(
        name = "库存流水管理",
        description = "库存流水的查询接口"
)
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

    @Operation(
            summary = "查询全部库存流水",
            description = "查询并返回系统中的全部库存流水"
    )
    @GetMapping
    public ApiResponse<List<InventoryTransaction>>
    findAll() {

        List<InventoryTransaction> transactions =
                inventoryTransactionService.findAll();

        return ApiResponse.success(transactions);
    }

    @Operation(
            summary = "查询指定商品的库存流水",
            description = "根据商品编号查询对应的库存流水"
    )
    @GetMapping("/product/{productId}")
    public ApiResponse<List<InventoryTransaction>>
    findByProductId(
            @Parameter(description = "商品编号", example = "1")
            @PathVariable @Positive(message = "商品编号必须大于0") Integer productId) {

        List<InventoryTransaction> transactions =
                inventoryTransactionService
                        .findByProductId(productId);

        return ApiResponse.success(transactions);
    }
}