package com.heshuang.security.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import com.heshuang.core.base.constant.RestExStatus;
import com.heshuang.core.base.enums.RespCode;
import com.heshuang.core.base.exception.BaseRuntimeException;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.exception.BusinessRtException;
import com.heshuang.core.base.result.RespBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Iterator;
import java.util.Set;

/**
 * @description:
 * @author: heshuang
 */

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OverallExceptionHandler {


    /**
     * 用户接口基本鉴权
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotLoginException.class)
    public RespBody handUserExceptionHandler(NotLoginException e) {
        e.printStackTrace();
        // 判断场景值，定制化异常信息
        String message = "";
        if (e.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供token";
        } else if (e.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "token无效";
        } else if (e.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "token已过期";
        } else if (e.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "token已被顶下线";
        } else if (e.getType().equals(NotLoginException.KICK_OUT)) {
            message = "token已被踢下线";
        } else {
            message = "当前会话未登录";
        }
        return RespBody.fail(RespCode.TOKEN_ERROR.getCode(), message);
    }

    /**
     * 权限
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotPermissionException.class)
    public RespBody handNotPermissionExceptionHandler(NotPermissionException e) {
        e.printStackTrace();
        return RespBody.fail(RespCode.PERM_ERROR.getCode(), e.getMessage());
    }

    /**
     * 角色
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NotRoleException.class)
    public RespBody handNotNotRoleExceptionHandler(NotRoleException e) {
        e.printStackTrace();
        return RespBody.fail(RespCode.ROLE_ERROR.getCode(), e.getMessage());
    }


    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBody handle(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        return RespBody.fail(String.valueOf(RestExStatus.MISSING_PARAMETERS.getValue()),"缺少[" + e.getParameterType() + "]类型的参数[" + e.getParameterName() + "]");
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBody handle(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return RespBody.fail(RestExStatus.PARSING_PARAMETERS.getReasonPhrase());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RespBody handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return RespBody.fail(String.valueOf(RestExStatus.IRREGULAR_PARAMETERS.getValue()), e.getMessage());
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBody handle(BindException e) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        return RespBody.fail(String.valueOf(RestExStatus.BIND_PARAMETERS.getValue()), String.format("参数绑定异常：%s:%s", field, code));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBody handle(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = (ConstraintViolation) violations.iterator().next();
        String message = violation.getMessage();
        return RespBody.fail(String.valueOf(RestExStatus.BIND_PARAMETERS.getValue()), String.format("参数验证异常：%s", message));
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespBody handle(ValidationException exception) {
        String errorMessage = "";
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) exception;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();

            ConstraintViolation item;
            for (Iterator var5 = violations.iterator(); var5.hasNext(); errorMessage = errorMessage + item.getMessage()) {
                item = (ConstraintViolation) var5.next();
            }
        }

        log.error(exception.getMessage(), exception);
        return RespBody.fail(String.valueOf(RestExStatus.VALIDATION_PARAMETERS.getValue()), errorMessage);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespBody handle(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return RespBody.fail(String.valueOf(RestExStatus.NOT_FOUNT.getValue()),RestExStatus.NOT_FOUNT.getReasonPhrase());
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RespBody handle(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return RespBody.fail(String.valueOf(RestExStatus.UN_SUPPORT_METHOD.getValue()),RestExStatus.UN_SUPPORT_METHOD.getReasonPhrase());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public RespBody handle(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return RespBody.fail(String.valueOf(RestExStatus.UN_SUPPORT_MEDIA.getValue()),RestExStatus.UN_SUPPORT_MEDIA.getReasonPhrase());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RespBody<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException( MethodArgumentNotValidException e ) ", e);
        Iterator var2 = e.getBindingResult().getAllErrors().iterator();
        StringBuffer sb = new StringBuffer();

        while (var2.hasNext()) {
            FieldError error = (FieldError) var2.next();
            sb.append("[").append(error.getField()).append(" ").append(error.getDefaultMessage()).append("] ");
        }

        RespBody<Object> restResult = new RespBody();
        restResult.setCode(String.valueOf(RestExStatus.IRREGULAR_PARAMETERS.getValue()));
        restResult.setMsg(sb.toString());
        return restResult;
    }

    @ExceptionHandler({DataAccessException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespBody<Void> dataAccessException(DataAccessException e) {
        log.error("dataAccessException(DataAccessException e)->", e);
        RespBody result = new RespBody();
        if (e instanceof DuplicateKeyException) {
            DuplicateKeyException exception = (DuplicateKeyException) e;
            result.setCode(String.valueOf(RestExStatus.EX_DATABASE.getValue()));
            result.setMsg(exception.getCause().getLocalizedMessage());
            return result;
        } else if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException exception = (DataIntegrityViolationException) e;
            result.setCode(String.valueOf(RestExStatus.EX_DATABASE.getValue()));
            result.setMsg(exception.getCause().getLocalizedMessage());
            return result;
        } else {
            result.setCode(String.valueOf(RestExStatus.EX_DATABASE.getValue()));
            result.setMsg(e.getClass().getCanonicalName());
            return result;
        }
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespBody<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("MaxUploadSizeExceededException handler ->", e.getMessage());
        RespBody<Object> restResult = new RespBody();
        long fileSize = e.getMaxUploadSize() / 1024L / 1024L;
        restResult.setMsg("最大文件上传不得超过" + fileSize + " M ");
        restResult.setCode("500");
        return restResult;
    }

    @ExceptionHandler({com.heshuang.core.base.exception.BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody<Object> handleBusinessException(BusinessException e) {
        log.error("Base Exception handler ->", e);
        RespBody<Object> restResult = new RespBody();
        restResult.setCode(String.valueOf(e.getErrorCode()));
        restResult.setMsg(e.getMessage());
        return restResult;
    }

    @ExceptionHandler({BusinessRtException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RespBody<Object> handleBusinessRtException(BusinessRtException e) {
        log.error("BusinessRtException -> {}", e.getMessage());
        RespBody<Object> restResult = new RespBody();
        restResult.setCode(String.valueOf(e.getErrorCode()));
        restResult.setMsg(e.getMessage());
        return restResult;
    }

    @ExceptionHandler({com.heshuang.core.base.exception.BaseRuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespBody<Object> handleBaseRuntimeException(BaseRuntimeException e) {
        log.error("handleBaseRuntimeException->", e);
        RespBody<Object> restResult = new RespBody();
        restResult.setCode(String.valueOf(e.getErrorCode()));
        restResult.setMsg(e.getMessage());
        return restResult;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RespBody<Object> handleException(Exception e) {
        log.error("unknown Exception handler ->", e);
        RespBody<Object> restResult = new RespBody();
        restResult.setMsg(e.getMessage());
        restResult.setCode("500");
        return restResult;
    }
}
