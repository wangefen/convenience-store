package com.store.conveniencestore.dto;

import java.util.List;

/**
 * 创建完整采购订单时，接收前端提交的数据。
 *
 * 一张采购订单包含：
 * 1. 一个供应商编号
 * 2. 多条采购明细
 */
public record PurchaseOrderCreateRequest(
        Integer supplierId,
        List<PurchaseItemCreateRequest> items
) {
}