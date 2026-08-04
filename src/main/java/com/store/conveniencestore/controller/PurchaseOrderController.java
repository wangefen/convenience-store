package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.dto.PurchaseOrderCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderResponse;
import com.store.conveniencestore.entity.PurchaseOrder;
import com.store.conveniencestore.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(
            PurchaseOrderService purchaseOrderService) {

        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public ApiResponse<List<PurchaseOrder>> findAll() {

        List<PurchaseOrder> purchaseOrders =
                purchaseOrderService.findAll();

        return ApiResponse.success(purchaseOrders);
    }

    @GetMapping("/{id}")
    public ApiResponse<PurchaseOrder> findById(
            @PathVariable Integer id) {

        PurchaseOrder purchaseOrder =
                purchaseOrderService.findById(id);

        return ApiResponse.success(purchaseOrder);
    }

    @PostMapping
    public ApiResponse<PurchaseOrderResponse> createOrder(
            @Valid @RequestBody PurchaseOrderCreateRequest request) {

        PurchaseOrderResponse response =
                purchaseOrderService.createOrder(request);

        return ApiResponse.success(response);
    }

    @PostMapping("/{id}/cancel")
    public ApiResponse<PurchaseOrder> cancel(
            @PathVariable Integer id) {

        PurchaseOrder cancelledOrder =
                purchaseOrderService.cancel(id);

        return ApiResponse.success(cancelledOrder);
    }
}