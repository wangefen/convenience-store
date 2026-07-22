package com.store.conveniencestore.dto;

import com.store.conveniencestore.entity.PurchaseItem;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 创建采购订单成功后，返回给前端的数据。
 */
public record PurchaseOrderResponse(
        Integer id,
        Integer supplierId,
        LocalDateTime purchaseTime,
        List<PurchaseItem> items
) {
}