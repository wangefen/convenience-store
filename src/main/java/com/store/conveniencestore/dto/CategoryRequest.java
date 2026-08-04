package com.store.conveniencestore.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * 接收新增、修改分类时提交的数据。
 */
public record CategoryRequest(

        @NotBlank(message = "分类名称不能为空")
        String name

) {
}