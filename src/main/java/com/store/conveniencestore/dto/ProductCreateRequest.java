package com.store.conveniencestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateRequest(

        @NotBlank(message = "商品名称不能为空")
        String name,

        @NotNull(message = "商品分类不能为空")
        @Positive(message = "商品分类编号必须大于0")
        Integer categoryId,

        @NotNull(message = "商品售价不能为空")
        @Positive(message = "商品售价必须大于0")
        BigDecimal salePrice

) {
}