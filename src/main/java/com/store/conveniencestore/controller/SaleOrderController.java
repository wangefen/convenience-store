package com.store.conveniencestore.controller;

import com.store.conveniencestore.dto.SaleOrderCreateRequest;
import com.store.conveniencestore.dto.SaleOrderResponse;
import com.store.conveniencestore.entity.SaleOrder;
import com.store.conveniencestore.service.SaleOrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 销售订单控制层。
 *
 * 负责接收前端发送的销售订单相关请求，
 * 再调用 SaleOrderService 完成业务操作。
 */
@RestController
@RequestMapping("/sale-orders")
public class SaleOrderController {

    private final SaleOrderService saleOrderService;

    /**
     * 构造器注入。
     *
     * Spring 会自动注入 SaleOrderServiceImpl 对象。
     */
    public SaleOrderController(SaleOrderService saleOrderService) {
        this.saleOrderService = saleOrderService;
    }

    /**
     * 查询全部销售订单。
     *
     * GET http://localhost:8080/sale-orders
     */
    @GetMapping
    public List<SaleOrder> findAll() {
        return saleOrderService.findAll();
    }

    /**
     * 根据 id 查询销售订单。
     *
     * GET http://localhost:8080/sale-orders/1
     */
    @GetMapping("/{id}")
    public SaleOrder findById(@PathVariable Integer id) {
        return saleOrderService.findById(id);
    }

    /**
     * 一次创建销售订单和多条销售明细。
     */
    @PostMapping
    public SaleOrderResponse createOrder(
            @RequestBody SaleOrderCreateRequest request) {

        return saleOrderService.createOrder(request);
    }


    /**
     * 取消销售订单。
     *
     * POST /sale-orders/1/cancel
     */
    @PostMapping("/{id}/cancel")
    public SaleOrder cancel(
            @PathVariable Integer id) {

        return saleOrderService.cancel(id);
    }
}