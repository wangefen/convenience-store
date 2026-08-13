package com.store.conveniencestore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * 接收新增、修改分类时提交的数据。
 */
public record CategoryRequest(

        @Schema(
                description = "分类名称",
                example = "饮料"
        )
        @NotBlank(message = "分类名称不能为空")
        String name

) {
}