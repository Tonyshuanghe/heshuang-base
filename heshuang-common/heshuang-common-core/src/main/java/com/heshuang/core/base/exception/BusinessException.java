package com.heshuang.core.base.exception;


import com.heshuang.core.base.constant.RestExStatus;

public class BusinessException extends BaseRuntimeException {
    private Integer code;

    public BusinessException(String message) {
        this(RestExStatus.EX_EXCEPTIONS.getValue(), message, (Throwable) null);
    }

    public BusinessException(String message, Throwable e) {
        this(RestExStatus.EX_EXCEPTIONS.getValue(), message, e);
    }

    public BusinessException(Integer code, String message) {
        this(code, message, (Throwable) null);
    }

    public BusinessException(Integer code, String message, Throwable e) {
        super(code, message, e);
        this.code = RestExStatus.EX_EXCEPTIONS.getValue();
        this.code = code;
    }

    public BusinessException(RestExStatus status) {
        this(status.getValue(), status.getReasonPhrase());
    }

    public BusinessException(RestExStatus status, Throwable e) {
        this(status.getValue(), status.getReasonPhrase(), e);
    }

    public static BusinessException of(RestExStatus status) {
        return new BusinessException(status);
    }

    public static BusinessException of(RestExStatus status, Throwable e) {
        return new BusinessException(status, e);
    }

    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }

    public static BusinessException of(Integer code, String message, Throwable e) {
        return new BusinessException(code, message, e);
    }

    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    public static BusinessException of(String message, Throwable e) {
        return new BusinessException(message, e);
    }

    public Integer getCode() {
        return this.code;
    }
}
