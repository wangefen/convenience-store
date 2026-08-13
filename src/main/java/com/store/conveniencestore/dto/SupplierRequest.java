package com.store.conveniencestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 接收新增、修改供应商时提交的数据。
 */
public record SupplierRequest(

        @Schema(
                description = "供应商名称",
                example = "广州食品批发有限公司"
        )
        @NotBlank(message = "供应商名称不能为空")
        String name,

        @Schema(
                description = "联系人姓名",
                example = "张三"
        )
        @NotBlank(message = "联系人不能为空")
        String contact,

        @Schema(
                description = "联系电话",
                example = "13800138000"
        )
        @NotBlank(message = "联系电话不能为空")
        @Pattern(
                regexp = "^[0-9+\\-() ]{6,20}$",
                message = "联系电话格式不正确"
        )
        String phone,

        @Schema(
                description = "供应商地址",
                example = "广州市白云区某某路 1 号"
        )
        @NotBlank(message = "供应商地址不能为空")
        String address

) {
}