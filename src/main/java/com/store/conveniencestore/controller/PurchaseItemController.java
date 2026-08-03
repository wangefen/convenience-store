package com.store.conveniencestore.controller;

import com.store.conveniencestore.common.ApiResponse;
import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.service.PurchaseItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchase-items")
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    public PurchaseItemController(
            PurchaseItemService purchaseItemService) {

        this.purchaseItemService = purchaseItemService;
    }

    @GetMapping
    public ApiResponse<List<PurchaseItem>> findAll() {

        List<PurchaseItem> purchaseItems =
                purchaseItemService.findAll();

        return ApiResponse.success(purchaseItems);
    }

    @GetMapping("/{id}")
    public ApiResponse<PurchaseItem> findById(
            @PathVariable Integer id) {

        PurchaseItem purchaseItem =
                purchaseItemService.findById(id);

        return ApiResponse.success(purchaseItem);
    }

    @GetMapping("/order/{purchaseOrderId}")
    public ApiResponse<List<PurchaseItem>>
    findByPurchaseOrderId(
            @PathVariable Integer purchaseOrderId) {

        List<PurchaseItem> purchaseItems =
                purchaseItemService
                        .findByPurchaseOrderId(purchaseOrderId);

        return ApiResponse.success(purchaseItems);
    }
}