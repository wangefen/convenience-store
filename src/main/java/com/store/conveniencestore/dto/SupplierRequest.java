package com.store.conveniencestore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * 接收新增、修改供应商时提交的数据。
 */
public record SupplierRequest(

        @NotBlank(message = "供应商名称不能为空")
        String name,

        @NotBlank(message = "联系人不能为空")
        String contact,

        @NotBlank(message = "联系电话不能为空")
        @Pattern(
                regexp = "^[0-9+\\-() ]{6,20}$",
                message = "联系电话格式不正确"
        )
        String phone,

        @NotBlank(message = "供应商地址不能为空")
        String address

) {
}