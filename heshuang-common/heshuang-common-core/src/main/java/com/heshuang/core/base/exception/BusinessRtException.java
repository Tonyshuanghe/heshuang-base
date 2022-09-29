//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.exception;


import com.heshuang.core.base.constant.RestExStatus;

public class BusinessRtException extends BaseRuntimeException {
    private Integer code;

    public BusinessRtException(String message) {
        this(RestExStatus.EX_EXCEPTIONS.getValue(), message, (Throwable) null);
    }

    public BusinessRtException(String message, Throwable e) {
        this(RestExStatus.EX_EXCEPTIONS.getValue(), message, e);
    }

    public BusinessRtException(Integer code, String message) {
        this(code, message, (Throwable) null);
    }

    public BusinessRtException(Integer code, String message, Throwable e) {
        super(code, message, e);
        this.code = RestExStatus.EX_EXCEPTIONS.getValue();
        this.code = code;
    }

    public BusinessRtException(RestExStatus status) {
        this(status.getValue(), status.getReasonPhrase());
    }

    public BusinessRtException(RestExStatus status, Throwable e) {
        this(status.getValue(), status.getReasonPhrase(), e);
    }

    public static BusinessRtException of(RestExStatus status) {
        return new BusinessRtException(status);
    }

    public static BusinessRtException of(RestExStatus status, Throwable e) {
        return new BusinessRtException(status, e);
    }

    public static BusinessRtException of(Integer code, String message) {
        return new BusinessRtException(code, message);
    }

    public static BusinessRtException of(Integer code, String message, Throwable e) {
        return new BusinessRtException(code, message, e);
    }

    public static BusinessRtException of(String message) {
        return new BusinessRtException(message);
    }

    public static BusinessRtException of(String message, Throwable e) {
        return new BusinessRtException(message, e);
    }

    public Integer getCode() {
        return this.code;
    }
}
