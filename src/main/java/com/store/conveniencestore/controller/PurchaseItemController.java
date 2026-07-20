package com.store.conveniencestore.controller;

import com.store.conveniencestore.entity.PurchaseItem;
import com.store.conveniencestore.service.PurchaseItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 采购明细控制层。
 *
 * 负责接收前端发送的采购明细相关 HTTP 请求，
 * 再调用 PurchaseItemService 完成相应操作。
 */
@RestController
@RequestMapping("/purchase-items")
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    /**
     * 构造器注入。
     *
     * Spring 会自动注入 PurchaseItemServiceImpl 对象。
     */
    public PurchaseItemController(
            PurchaseItemService purchaseItemService) {
        this.purchaseItemService = purchaseItemService;
    }

    /**
     * 查询全部采购明细。
     *
     * GET http://localhost:8080/purchase-items
     */
    @GetMapping
    public List<PurchaseItem> findAll() {
        return purchaseItemService.findAll();
    }

    /**
     * 根据采购明细 id 查询。
     *
     * GET http://localhost:8080/purchase-items/1
     */
    @GetMapping("/{id}")
    public PurchaseItem findById(@PathVariable Integer id) {
        return purchaseItemService.findById(id);
    }

    /**
     * 根据采购订单 id 查询该订单的全部明细。
     *
     * GET http://localhost:8080/purchase-items/order/1
     */
    @GetMapping("/order/{purchaseOrderId}")
    public List<PurchaseItem> findByPurchaseOrderId(
            @PathVariable Integer purchaseOrderId) {

        return purchaseItemService
                .findByPurchaseOrderId(purchaseOrderId);
    }

    /**
     * 新增采购明细。
     *
     * POST http://localhost:8080/purchase-items
     */
    @PostMapping
    public PurchaseItem insert(
            @RequestBody PurchaseItem purchaseItem) {

        purchaseItemService.insert(purchaseItem);

        // 返回包含数据库自增 id 的采购明细对象
        return purchaseItem;
    }

    /**
     * 修改采购明细。
     *
     * PUT http://localhost:8080/purchase-items/1
     */
    @PutMapping("/{id}")
    public PurchaseItem update(
            @PathVariable Integer id,
            @RequestBody PurchaseItem purchaseItem) {

        // 路径中的 id 表示修改哪一条明细
        purchaseItem.setId(id);

        purchaseItemService.update(purchaseItem);

        // 修改后重新查询并返回最新数据
        return purchaseItemService.findById(id);
    }

    /**
     * 删除采购明细。
     *
     * DELETE http://localhost:8080/purchase-items/1
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        purchaseItemService.delete(id);
    }
}