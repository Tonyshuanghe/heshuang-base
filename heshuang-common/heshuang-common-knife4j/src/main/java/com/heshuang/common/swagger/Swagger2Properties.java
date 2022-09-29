//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.common.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@ConfigurationProperties("swagger2")
public class Swagger2Properties {
    public static final String PREFIX = "swagger2";
    private String basePackage = "com.heshuang";
    private String title;
    private String description;
    private String termsOfServiceUrl;
    private String contact;
    private String version;
    private String customBasePath = "/";
    private boolean showHeader = true;
    private String nickNamePolicy = "default";
    private List<String> headers = Arrays.asList( "Authorization");

    public Swagger2Properties() {
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTermsOfServiceUrl(String termsOfServiceUrl) {
        this.termsOfServiceUrl = termsOfServiceUrl;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCustomBasePath(String customBasePath) {
        this.customBasePath = customBasePath;
    }

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public void setNickNamePolicy(String nickNamePolicy) {
        this.nickNamePolicy = nickNamePolicy;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getBasePackage() {
        return this.basePackage;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTermsOfServiceUrl() {
        return this.termsOfServiceUrl;
    }

    public String getContact() {
        return this.contact;
    }

    public String getVersion() {
        return this.version;
    }

    public String getCustomBasePath() {
        return this.customBasePath;
    }

    public boolean isShowHeader() {
        return this.showHeader;
    }

    public String getNickNamePolicy() {
        return this.nickNamePolicy;
    }

    public List<String> getHeaders() {
        return this.headers;
    }
}
