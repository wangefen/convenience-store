package com.store.conveniencestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * 创建采购明细时，接收前端提交的数据。
 *
 * 不需要接收 id，因为 id 由数据库自动生成。
 * 不需要接收 purchaseOrderId，因为它由后端在创建订单后自动填写。
 */
public record PurchaseItemCreateRequest(
        @Schema(
                description = "商品编号",
                example = "1"
        )
        @NotNull(message = "商品编号不能为空")
        @Positive(message = "商品编号必须大于0")
        Integer productId,

        @Schema(
                description = "采购数量",
                example = "10"
        )
        @NotNull(message = "采购数量不能为空")
        @Positive(message = "采购数量必须大于0")
        Integer quantity,

        @Schema(
                description = "采购价格",
                example = "2.50"
        )
        @NotNull(message = "采购价格不能为空")
        @Positive(message = "采购价格必须大于0")
        BigDecimal purchasePrice
) {
}