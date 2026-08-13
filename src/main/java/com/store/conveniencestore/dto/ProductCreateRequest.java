package com.store.conveniencestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductCreateRequest(

        @Schema(
                description = "商品名称",
                example = "矿泉水"
        )
        @NotBlank(message = "商品名称不能为空")
        String name,

        @Schema(
                description = "商品所属分类的编号",
                example = "1"
        )
        @NotNull(message = "商品分类不能为空")
        @Positive(message = "商品分类编号必须大于0")
        Integer categoryId,

        @Schema(
                description = "商品销售价格",
                example = "3.50"
        )
        @NotNull(message = "商品售价不能为空")
        @Positive(message = "商品售价必须大于0")
        BigDecimal salePrice

) {
}