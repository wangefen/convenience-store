package com.store.conveniencestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * 创建销售明细时，接收前端提交的数据。
 */
public record SaleItemCreateRequest(

        @Schema(
                description = "商品编号",
                example = "1"
        )
        @NotNull(message = "商品编号不能为空")
        @Positive(message = "商品编号必须大于0")
        Integer productId,

        @Schema(
                description = "销售数量",
                example = "2"
        )
        @NotNull(message = "销售数量不能为空")
        @Positive(message = "销售数量必须大于0")
        Integer quantity

) {
}