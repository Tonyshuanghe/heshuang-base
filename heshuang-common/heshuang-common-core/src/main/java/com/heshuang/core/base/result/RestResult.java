//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.result;


import com.heshuang.core.base.constant.RestExStatus;

public class RestResult<T> {
    private String msg;
    private Boolean succeed;
    private T data;
    private Integer code;

    public static <T> RestResult success(T data) {
        return new RestResult(RestExStatus.SUCCESS, data);
    }

    public static <T> RestResult failed(T data) {
        return new RestResult(RestExStatus.FAIL, data);
    }

    public static <T> RestResult failed() {
        return new RestResult(RestExStatus.FAIL);
    }

    public static RestResult of(RestExStatus status) {
        return new RestResult(status);
    }

    public static RestResult of(Integer code, String message) {
        return new RestResult(code, message);
    }

    public static RestResult of(String message) {
        return new RestResult(message);
    }

    public static <T> RestResult<T> of(String message, T data) {
        return new RestResult(message, data);
    }

    public RestResult(String message) {
        this();
        this.setMsg(message);
    }

    public RestResult(RestExStatus status) {
        this.succeed = true;
        this.code = RestExStatus.SUCCESS.getValue();
        this.setSucceed(status.getValue() == RestExStatus.SUCCESS.getValue());
        this.setCode(status.getValue());
        this.setMsg(status.getReasonPhrase());
    }

    public RestResult(RestExStatus status, T data) {
        this.succeed = true;
        this.code = RestExStatus.SUCCESS.getValue();
        this.setData(data);
        this.setSucceed(status.getValue() == RestExStatus.SUCCESS.getValue());
        this.setCode(status.getValue());
        this.setMsg(status.getReasonPhrase());
    }

    public RestResult(Integer code, String message, T data) {
        this.succeed = true;
        this.code = RestExStatus.SUCCESS.getValue();
        this.setData(data);
        this.setSucceed(RestExStatus.SUCCESS.getValue() == code);
        this.setCode(code);
        this.setMsg(message);
    }

    public RestResult(String message, T data) {
        this.succeed = true;
        this.code = RestExStatus.SUCCESS.getValue();
        this.setData(data);
        this.setSucceed(true);
        this.setCode(RestExStatus.SUCCESS.getValue());
        this.setMsg(message);
    }

    public RestResult(Integer code, String message) {
        this.succeed = true;
        this.code = RestExStatus.SUCCESS.getValue();
        this.setSucceed(RestExStatus.SUCCESS.getValue() == code);
        this.setCode(RestExStatus.SUCCESS.getValue());
        this.setMsg(message);
    }

    public RestResult() {
        this.succeed = true;
        this.code = RestExStatus.SUCCESS.getValue();
        this.setSucceed(true);
        this.setCode(RestExStatus.SUCCESS.getValue());
        this.setMsg(RestExStatus.SUCCESS.getReasonPhrase());
    }

    public String getMsg() {
        return this.msg;
    }

    public Boolean getSucceed() {
        return this.succeed;
    }

    public T getData() {
        return this.data;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setSucceed(Boolean succeed) {
        this.succeed = succeed;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof RestResult)) {
            return false;
        } else {
            RestResult<?> other = (RestResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59:
                {
                    Object this$msg = this.getMsg();
                    Object other$msg = other.getMsg();
                    if (this$msg == null) {
                        if (other$msg == null) {
                            break label59;
                        }
                    } else if (this$msg.equals(other$msg)) {
                        break label59;
                    }

                    return false;
                }

                Object this$succeed = this.getSucceed();
                Object other$succeed = other.getSucceed();
                if (this$succeed == null) {
                    if (other$succeed != null) {
                        return false;
                    }
                } else if (!this$succeed.equals(other$succeed)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$code = this.getCode();
                Object other$code = other.getCode();
                if (this$code == null) {
                    if (other$code != null) {
                        return false;
                    }
                } else if (!this$code.equals(other$code)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof RestResult;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $succeed = this.getSucceed();
        result = result * 59 + ($succeed == null ? 43 : $succeed.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RestResult(msg=" + this.getMsg() + ", succeed=" + this.getSucceed() + ", data=" + this.getData() + ", code=" + this.getCode() + ")";
    }
}
