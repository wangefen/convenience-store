package com.store.conveniencestore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * 接收创建整张销售订单的数据。
 */
public record SaleOrderCreateRequest(

        @NotEmpty(message = "销售订单至少包含一条明细")
        List<@NotNull(message = "销售明细不能为空") @Valid SaleItemCreateRequest> items

) {
}