package com.store.conveniencestore.exception;

/**
 * 表示当前操作与业务资源的最新状态发生冲突。
 *
 * 例如：
 * 多个请求同时取消同一张订单，
 * 其中一个请求成功后，其他请求取消失败。
 *
 * 最终对应 HTTP 409 状态码。
 */
public class BusinessConflictException
        extends RuntimeException {

    /**
     * 创建业务冲突异常。
     *
     * @param message 具体的错误提示信息
     */
    public BusinessConflictException(String message) {
        super(message);
    }
}