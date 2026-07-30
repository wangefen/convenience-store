package com.store.conveniencestore.exception;

/**
 * 表示请求的业务资源不存在。
 *
 * 例如：
 * 商品不存在、供应商不存在、采购订单不存在、
 * 销售订单不存在等情况。
 *
 * 最终对应 HTTP 404 状态码。
 */
public class ResourceNotFoundException
        extends RuntimeException {

    /**
     * 创建资源不存在异常。
     *
     * @param message 具体的错误提示信息
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}