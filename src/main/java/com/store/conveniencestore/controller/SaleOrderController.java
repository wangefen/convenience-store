package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.SaleOrderCreateRequest;
import com.store.conveniencestore.dto.SaleOrderResponse;
import com.store.conveniencestore.entity.SaleOrder;
import com.store.conveniencestore.service.SaleOrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@Tag(
        name = "销售订单管理",
        description = "销售订单的查询、创建和取消接口"
)
@RestController
@RequestMapping("/sale-orders")
public class SaleOrderController {

    private final SaleOrderService saleOrderService;

    public SaleOrderController(
            SaleOrderService saleOrderService) {

        this.saleOrderService = saleOrderService;
    }

    @Operation(
            summary = "查询全部销售订单",
            description = "查询并返回系统中的全部销售订单"
    )
    @GetMapping
    public ApiResponse<List<SaleOrder>> findAll() {

        List<SaleOrder> saleOrders =
                saleOrderService.findAll();

        return ApiResponse.success(saleOrders);
    }

    @Operation(
            summary = "根据编号查询销售订单",
            description = "根据销售订单编号查询对应的销售订单信息"
    )
    @GetMapping("/{id}")
    public ApiResponse<SaleOrder> findById(
            @Parameter(description = "销售订单编号", example = "1")
            @PathVariable @Positive(message = "销售订单编号必须大于0") Integer id) {

        SaleOrder saleOrder =
                saleOrderService.findById(id);

        return ApiResponse.success(saleOrder);
    }

    @Operation(
            summary = "创建销售订单",
            description = "接收销售订单信息并创建一个新的销售订单及销售明细"
    )
    @PostMapping
    public ApiResponse<SaleOrderResponse> createOrder(
            @Valid @RequestBody SaleOrderCreateRequest request) {

        SaleOrderResponse response =
                saleOrderService.createOrder(request);

        return ApiResponse.success(response);
    }

    @Operation(
            summary = "取消销售订单",
            description = "根据销售订单编号取消对应的销售订单"
    )
    @PostMapping("/{id}/cancel")
    public ApiResponse<SaleOrder> cancel(
            @Parameter(description = "需要取消的销售订单编号", example = "1")
            @PathVariable @Positive(message = "销售订单编号必须大于0") Integer id) {

        SaleOrder cancelledOrder =
                saleOrderService.cancel(id);

        return ApiResponse.success(cancelledOrder);
    }
}