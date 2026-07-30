package com.store.conveniencestore.controller;

import com.store.conveniencestore.dto.PurchaseOrderCreateRequest;
import com.store.conveniencestore.dto.PurchaseOrderResponse;
import com.store.conveniencestore.entity.PurchaseOrder;
import com.store.conveniencestore.service.PurchaseOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购订单控制层。
 *
 * 负责接收前端发送的采购订单 HTTP 请求，
 * 再调用 PurchaseOrderService 完成相应操作。
 */
@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    /**
     * 构造器注入。
     *
     * Spring 会自动注入 PurchaseOrderServiceImpl 对象。
     */
    public PurchaseOrderController(
            PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    /**
     * 查询全部采购订单。
     *
     * GET http://localhost:8080/purchase-orders
     */
    @GetMapping
    public List<PurchaseOrder> findAll() {
        return purchaseOrderService.findAll();
    }

    /**
     * 根据 id 查询采购订单。
     *
     * GET http://localhost:8080/purchase-orders/1
     */
    @GetMapping("/{id}")
    public PurchaseOrder findById(@PathVariable Integer id) {
        return purchaseOrderService.findById(id);
    }

    /**
     * 新增采购订单。
     *
     * POST http://localhost:8080/purchase-orders
     */
    @PostMapping
    public PurchaseOrderResponse createOrder(@RequestBody PurchaseOrderCreateRequest request){
        return purchaseOrderService.createOrder(request);
    }

    /**
     * 取消采购订单。
     *
     * POST /purchase-orders/1/cancel
     */
    @PostMapping("/{id}/cancel")
    public PurchaseOrder cancel(
            @PathVariable Integer id) {

        return purchaseOrderService.cancel(id);
    }
}