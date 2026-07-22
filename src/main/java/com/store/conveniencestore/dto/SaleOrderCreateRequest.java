package com.store.conveniencestore.dto;

import java.util.List;

/**
 * 接收创建整张销售订单的数据。
 */
public record SaleOrderCreateRequest(
        List<SaleItemCreateRequest> items
) {
}