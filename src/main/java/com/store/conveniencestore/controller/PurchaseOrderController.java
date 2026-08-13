package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.PurchaseOrderCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderResponse;
import com.store.conveniencestore.entity.PurchaseOrder;
import com.store.conveniencestore.service.PurchaseOrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Tag(
        name = "采购订单管理",
        description = "采购订单的查询、创建和取消接口"
)
@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(
            PurchaseOrderService purchaseOrderService) {

        this.purchaseOrderService = purchaseOrderService;
    }

    @Operation(
            summary = "查询全部采购订单",
            description = "查询并返回系统中的全部采购订单"
    )
    @GetMapping
    public ApiResponse<List<PurchaseOrder>> findAll() {

        List<PurchaseOrder> purchaseOrders =
                purchaseOrderService.findAll();

        return ApiResponse.success(purchaseOrders);
    }

    @Operation(
            summary = "根据编号查询采购订单",
            description = "根据采购订单编号查询对应的采购订单信息"
    )
    @GetMapping("/{id}")
    public ApiResponse<PurchaseOrder> findById(
            @Parameter(description = "采购订单编号", example = "1")
            @PathVariable @Positive(message = "采购订单编号必须大于0") Integer id) {

        PurchaseOrder purchaseOrder =
                purchaseOrderService.findById(id);

        return ApiResponse.success(purchaseOrder);
    }

    @Operation(
            summary = "创建采购订单",
            description = "接收采购订单信息并创建一个新的采购订单"
    )
    @PostMapping
    public ApiResponse<PurchaseOrderResponse> createOrder(
            @Valid @RequestBody PurchaseOrderCreateRequest request) {

        PurchaseOrderResponse response =
                purchaseOrderService.createOrder(request);

        return ApiResponse.success(response);
    }

    @Operation(
            summary = "取消采购订单",
            description = "根据采购订单编号取消对应的采购订单"
    )
    @PostMapping("/{id}/cancel")
    public ApiResponse<PurchaseOrder> cancel(
            @Parameter(description = "需要取消的采购订单编号", example = "1")
            @PathVariable @Positive(message = "采购订单编号必须大于0") Integer id) {

        PurchaseOrder cancelledOrder =
                purchaseOrderService.cancel(id);

        return ApiResponse.success(cancelledOrder);
    }
}