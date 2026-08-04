package com.store.conveniencestore.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 创建销售明细时，接收前端提交的数据。
 */
public record SaleItemCreateRequest(

        @NotNull(message = "商品编号不能为空")
        @Positive(message = "商品编号必须大于0")
        Integer productId,

        @NotNull(message = "销售数量不能为空")
        @Positive(message = "销售数量必须大于0")
        Integer quantity

) {
}