//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.businessstart.supports;



import com.heshuang.core.base.constant.RestExStatus;
import com.heshuang.core.base.exception.BaseRuntimeException;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.exception.BusinessRtException;
import com.heshuang.core.base.result.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Iterator;
import java.util.Set;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    public ExceptionControllerAdvice() {
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handle(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e);
        RestResult result = RestResult.of(RestExStatus.MISSING_PARAMETERS);
        result.setMsg("缺少[" + e.getParameterType() + "]类型的参数[" + e.getParameterName() + "]");
        return RestResult.of(RestExStatus.MISSING_PARAMETERS);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handle(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return RestResult.of(RestExStatus.PARSING_PARAMETERS);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RestResult handle(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return RestResult.of(RestExStatus.IRREGULAR_PARAMETERS.getValue(), e.getMessage());
    }

    @ExceptionHandler({BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handle(BindException e) {
        log.error(e.getMessage(), e);
        BindingResult result = e.getBindingResult();
        FieldError error = result.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        return RestResult.of(RestExStatus.BIND_PARAMETERS.getValue(), String.format("参数绑定异常：%s:%s", field, code));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handle(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = (ConstraintViolation) violations.iterator().next();
        String message = violation.getMessage();
        return RestResult.of(RestExStatus.BIND_PARAMETERS.getValue(), String.format("参数验证异常：%s", message));
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestResult handle(ValidationException exception) {
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
        return RestResult.of(RestExStatus.VALIDATION_PARAMETERS.getValue(), errorMessage);
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RestResult handle(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        return RestResult.of(RestExStatus.NOT_FOUNT);
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RestResult handle(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return RestResult.of(RestExStatus.UN_SUPPORT_METHOD);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public RestResult handle(HttpMediaTypeNotSupportedException e) {
        log.error(e.getMessage(), e);
        return RestResult.of(RestExStatus.UN_SUPPORT_MEDIA);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException( MethodArgumentNotValidException e ) ", e);
        Iterator var2 = e.getBindingResult().getAllErrors().iterator();
        StringBuffer sb = new StringBuffer();

        while (var2.hasNext()) {
            FieldError error = (FieldError) var2.next();
            sb.append("[").append(error.getField()).append(" ").append(error.getDefaultMessage()).append("] ");
        }

        RestResult<Object> restResult = new RestResult();
        restResult.setCode(RestExStatus.IRREGULAR_PARAMETERS.getValue());
        restResult.setMsg(sb.toString());
        restResult.setSucceed(false);
        return restResult;
    }

    @ExceptionHandler({DataAccessException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult<Void> dataAccessException(DataAccessException e) {
        log.error("dataAccessException(DataAccessException e)->", e);
        RestResult result = new RestResult();
        if (e instanceof DuplicateKeyException) {
            DuplicateKeyException exception = (DuplicateKeyException) e;
            result.setCode(RestExStatus.EX_DATABASE.getValue());
            result.setMsg(exception.getCause().getLocalizedMessage());
            result.setSucceed(false);
            return result;
        } else if (e instanceof DataIntegrityViolationException) {
            DataIntegrityViolationException exception = (DataIntegrityViolationException) e;
            result.setCode(RestExStatus.EX_DATABASE.getValue());
            result.setMsg(exception.getCause().getLocalizedMessage());
            result.setSucceed(false);
            return result;
        } else {
            result.setCode(RestExStatus.EX_DATABASE.getValue());
            result.setSucceed(false);
            result.setMsg(e.getClass().getCanonicalName());
            return result;
        }
    }

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestResult<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("MaxUploadSizeExceededException handler ->", e.getMessage());
        RestResult<Object> restResult = new RestResult();
        restResult.setSucceed(false);
        long fileSize = e.getMaxUploadSize() / 1024L / 1024L;
        restResult.setMsg("最大文件上传不得超过" + fileSize + " M ");
        restResult.setCode(500);
        return restResult;
    }

    @ExceptionHandler({BusinessException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestResult<Object> handleBusinessException(BusinessException e) {
        log.error("Base Exception handler ->", e);
        RestResult<Object> restResult = new RestResult();
        restResult.setCode(e.getErrorCode());
        restResult.setMsg(e.getMessage());
        restResult.setSucceed(false);
        return restResult;
    }

    @ExceptionHandler({BusinessRtException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public RestResult<Object> handleBusinessRtException(BusinessRtException e) {
        log.error("BusinessRtException -> {}", e.getMessage());
        RestResult<Object> restResult = new RestResult();
        restResult.setCode(e.getErrorCode());
        restResult.setMsg(e.getMessage());
        restResult.setSucceed(false);
        return restResult;
    }

    @ExceptionHandler({BaseRuntimeException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestResult<Object> handleBaseRuntimeException(BaseRuntimeException e) {
        log.error("handleBaseRuntimeException->", e);
        RestResult<Object> restResult = new RestResult();
        restResult.setCode(e.getErrorCode());
        restResult.setMsg(e.getMessage());
        restResult.setSucceed(false);
        return restResult;
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public RestResult<Object> handleException(Exception e) {
        log.error("unknown Exception handler ->", e);
        RestResult<Object> restResult = new RestResult();
        restResult.setSucceed(false);
        restResult.setMsg(e.getMessage());
        restResult.setCode(500);
        return restResult;
    }
}
