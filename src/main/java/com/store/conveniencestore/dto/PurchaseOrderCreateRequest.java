package com.store.conveniencestore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

/**
 * 创建完整采购订单时，接收前端提交的数据。
 *
 * 一张采购订单包含：
 * 1. 一个供应商编号
 * 2. 多条采购明细
 */
public record PurchaseOrderCreateRequest(
        @NotNull(message = "供应商编号不能为空")
        @Positive(message = "供应商编号必须大于0")
        Integer supplierId,


        /*@Valid 会让校验继续进入每个 PurchaseItemCreateRequest，
        检查其中的：productId,quantity,purchasePrice*/
        @NotEmpty(message = "采购订单至少包含一条明细")
        List<@NotNull(message = "采购明细不能为空") @Valid PurchaseItemCreateRequest> items
) {
}