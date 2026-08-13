package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.service.PurchaseItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Tag(
        name = "采购明细管理",
        description = "采购明细的查询接口"
)
@RestController
@RequestMapping("/purchase-items")
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    public PurchaseItemController(
            PurchaseItemService purchaseItemService) {

        this.purchaseItemService = purchaseItemService;
    }

    @Operation(
            summary = "查询全部采购明细",
            description = "查询并返回系统中的全部采购明细"
    )
    @GetMapping
    public ApiResponse<List<PurchaseItem>> findAll() {

        List<PurchaseItem> purchaseItems =
                purchaseItemService.findAll();

        return ApiResponse.success(purchaseItems);
    }

    @Operation(
            summary = "根据编号查询采购明细",
            description = "根据采购明细编号查询对应的采购明细信息"
    )
    @GetMapping("/{id}")
    public ApiResponse<PurchaseItem> findById(
            @Parameter(description = "采购明细编号", example = "1")
            @PathVariable @Positive(message = "采购明细编号必须大于0") Integer id) {

        PurchaseItem purchaseItem =
                purchaseItemService.findById(id);

        return ApiResponse.success(purchaseItem);
    }

    @Operation(
            summary = "查询指定采购订单的明细",
            description = "根据采购订单编号查询该订单下的全部采购明细"
    )
    @GetMapping("/order/{purchaseOrderId}")
    public ApiResponse<List<PurchaseItem>>
    findByPurchaseOrderId(
            @Parameter(description = "采购订单编号", example = "1")
            @PathVariable @Positive(message = "采购订单编号必须大于0") Integer purchaseOrderId) {

        List<PurchaseItem> purchaseItems =
                purchaseItemService
                        .findByPurchaseOrderId(purchaseOrderId);

        return ApiResponse.success(purchaseItems);
    }
}