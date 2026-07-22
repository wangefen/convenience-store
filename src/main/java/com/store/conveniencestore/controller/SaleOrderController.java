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
     * 修改销售订单。
     *
     * PUT http://localhost:8080/sale-orders/1
     */
    @PutMapping("/{id}")
    public SaleOrder update(
            @PathVariable Integer id,
            @RequestBody SaleOrder saleOrder) {

        // 路径中的 id 表示修改哪一条销售订单
        saleOrder.setId(id);

        saleOrderService.update(saleOrder);

        return saleOrderService.findById(id);
    }

    /**
     * 删除销售订单。
     *
     * DELETE http://localhost:8080/sale-orders/1
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        saleOrderService.delete(id);
    }
}