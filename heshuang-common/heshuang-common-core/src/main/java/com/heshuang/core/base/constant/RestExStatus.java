//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.constant;

public enum RestExStatus {
    SUCCESS(200, "请求成功"),
    FAIL(501, "操作失败"),
    MISSING_PARAMETERS(40001, "请求缺少参数"),
    PARSING_PARAMETERS(40002, "参数解析异常"),
    VALIDATION_PARAMETERS(40003, "参数验证异常"),
    BIND_PARAMETERS(40004, "参数绑定异常"),
    IRREGULAR_PARAMETERS(40005, "参数不符合要求"),
    UN_DATA_AUTH(401, "无数据权限"),
    UN_RESOURCE_AUTH(403, "无权限访问资源"),
    NOT_FOUNT(404, "未找到资源"),
    UN_SUPPORT_METHOD(405, "不支持当前请求方法"),
    UN_SUPPORT_MEDIA(415, "不支持当前媒体类型"),
    EX_DATABASE(500101, "数据库异常"),
    EX_EXCEPTIONS(500102, "其他服务器异常"),
    SERVICE_LOCKED(500201, "资源已被锁"),
    BUSINESS_EXCEPTIONS(5003, "其他业务异常");

    private final int value;
    private final String reasonPhrase;

    private RestExStatus(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
}
