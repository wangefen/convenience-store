package com.store.conveniencestore.common;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        Integer code,
        String message,
        T data,
        LocalDateTime timestamp
){

    /**
     * 创建成功相应
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                200,
                "操作成功",
                data,
                LocalDateTime.now()
        );
    }

    /**
     * 创建失败相应
     */
    public static  ApiResponse<Void> error(
            Integer code,
            String  message){
        return new ApiResponse<>(
                code,
                message,
                null,
                LocalDateTime.now()
        );
    }

}
