package com.store.conveniencestore.dto;

import com.store.conveniencestore.entity.SaleItem;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 创建销售订单成功后返回给前端的数据。
 */
public record SaleOrderResponse(
        Integer id,
        LocalDateTime saleTime,
        String status,
        List<SaleItem> items
) {
}