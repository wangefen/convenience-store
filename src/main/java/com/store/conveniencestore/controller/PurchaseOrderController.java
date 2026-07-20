package com.store.conveniencestore.controller;

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
    public PurchaseOrder insert(
            @RequestBody PurchaseOrder purchaseOrder) {

        purchaseOrderService.insert(purchaseOrder);

        // 返回包含数据库自增 id 的对象
        return purchaseOrder;
    }

    /**
     * 修改采购订单。
     *
     * PUT http://localhost:8080/purchase-orders/1
     */
    @PutMapping("/{id}")
    public PurchaseOrder update(
            @PathVariable Integer id,
            @RequestBody PurchaseOrder purchaseOrder) {

        // 路径里的 id 表示修改哪一条采购订单
        purchaseOrder.setId(id);

        purchaseOrderService.update(purchaseOrder);

        // 修改后重新查询最新数据
        return purchaseOrderService.findById(id);
    }

    /**
     * 删除采购订单。
     *
     * DELETE http://localhost:8080/purchase-orders/1
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        purchaseOrderService.delete(id);
    }
}