package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.SaleOrderCreateRequest;
import com.store.conveniencestore.dto.SaleOrderResponse;
import com.store.conveniencestore.entity.SaleOrder;
import com.store.conveniencestore.service.SaleOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale-orders")
public class SaleOrderController {

    private final SaleOrderService saleOrderService;

    public SaleOrderController(
            SaleOrderService saleOrderService) {

        this.saleOrderService = saleOrderService;
    }

    /**
     * 查询全部销售订单。
     */
    @GetMapping
    public ApiResponse<List<SaleOrder>> findAll() {

        List<SaleOrder> saleOrders =
                saleOrderService.findAll();

        return ApiResponse.success(saleOrders);
    }

    /**
     * 根据编号查询销售订单。
     */
    @GetMapping("/{id}")
    public ApiResponse<SaleOrder> findById(
            @PathVariable Integer id) {

        SaleOrder saleOrder =
                saleOrderService.findById(id);

        return ApiResponse.success(saleOrder);
    }

    /**
     * 创建销售订单和销售明细。
     */
    @PostMapping
    public ApiResponse<SaleOrderResponse> createOrder(
            @Valid @RequestBody SaleOrderCreateRequest request) {

        SaleOrderResponse response =
                saleOrderService.createOrder(request);

        return ApiResponse.success(response);
    }

    /**
     * 取消销售订单。
     */
    @PostMapping("/{id}/cancel")
    public ApiResponse<SaleOrder> cancel(
            @PathVariable @Positive(message = "销售订单编号必须大于0") Integer id) {

        SaleOrder cancelledOrder =
                saleOrderService.cancel(id);

        return ApiResponse.success(cancelledOrder);
    }
}