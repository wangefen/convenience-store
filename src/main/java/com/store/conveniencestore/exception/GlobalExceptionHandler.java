package com.store.conveniencestore.exception;

import com.store.conveniencestore.common.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


/**
 * 异常逻辑：
 * Service 抛出异常
 * → Controller 没有处理，异常继续向外传播
 * → Spring MVC 捕获异常
 * → 找到带有 @RestControllerAdvice 的类
 * → 根据异常类型匹配 @ExceptionHandler
 * → 自动调用对应方法
 * → 方法生成状态码和 JSON
 * → Spring 返回给前端
 */


/**
 * 全局异常处理器。
 *
 * 统一捕获 Controller 调用过程中产生的异常，
 * 并转换成统一的 HTTP 状态码和 JSON 响应。
 *@RestControllerAdvice:这个类是全局异常处理类，项目中 Controller 出现且未被处理的异常，可以到这里寻找处理方法。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理资源不存在异常，返回 HTTP 404。
     * @ExceptionHandler:如果出现 ResourceNotFoundException，就调用下面这个方法处理。
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(
            ResourceNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()
                ));
    }

    /**
     * 处理业务冲突异常，返回 HTTP 409。
     */
    @ExceptionHandler(BusinessConflictException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessConflict(
            BusinessConflictException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()
                ));
    }

    /**
     * 处理业务参数错误，返回 HTTP 400。
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(
            IllegalArgumentException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "参数格式错误：" + exception.getName()
                ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleUnreadableBody(
            HttpMessageNotReadableException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        "请求体格式错误"
                ));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleDataIntegrityViolation(
            DataIntegrityViolationException exception) {

        log.warn("发生数据库约束冲突", exception);

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(
                        HttpStatus.CONFLICT.value(),
                        "数据存在关联关系或重复，当前操作无法完成"
                ));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleMethodNotSupported(
            HttpRequestMethodNotSupportedException exception) {

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.error(
                        HttpStatus.METHOD_NOT_ALLOWED.value(),
                        "当前接口不支持该请求方式"
                ));
    }


    /**
     * 处理 @Valid 请求参数校验失败。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {

        String message = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("请求参数校验失败");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        message
                ));
    }



    /**
     * 处理路径参数、查询参数等方法参数校验失败。
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Void>>
    handleHandlerMethodValidation(
            HandlerMethodValidationException exception) {

        String message = exception
                .getAllErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("请求参数校验失败");

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(
                        HttpStatus.BAD_REQUEST.value(),
                        message
                ));
    }

    /**
     * 处理其他未预料的异常，返回 HTTP 500。
     * 这里的类型是父类Exception，用来兜底的
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(
            Exception exception) {

        log.error("发生未处理的系统异常", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "服务器内部错误，请稍后重试"
                ));
    }
}