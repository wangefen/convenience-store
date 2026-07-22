package com.store.conveniencestore.dto;

import java.math.BigDecimal;

/**
 * 创建采购明细时，接收前端提交的数据。
 *
 * 不需要接收 id，因为 id 由数据库自动生成。
 * 不需要接收 purchaseOrderId，因为它由后端在创建订单后自动填写。
 */
public record PurchaseItemCreateRequest(
        Integer productId,
        Integer quantity,
        BigDecimal purchasePrice
) {
}