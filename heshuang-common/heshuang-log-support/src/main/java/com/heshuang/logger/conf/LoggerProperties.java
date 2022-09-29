//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("heshuang.logger")
public class LoggerProperties {
    private String extend = "";
    private String projectIdHeader = "x-project-id";
    private String projectNoHeader = "x-project-no";
    private String topic = "heshuang.logger.action";
    private String beanPackageScan = "com.heshuang";
    private String businessCode = "####";
    private Boolean enabled = false;

    public LoggerProperties() {
    }

    public String getExtend() {
        return this.extend;
    }

    public String getProjectIdHeader() {
        return this.projectIdHeader;
    }

    public String getProjectNoHeader() {
        return this.projectNoHeader;
    }

    public String getTopic() {
        return this.topic;
    }

    public String getBeanPackageScan() {
        return this.beanPackageScan;
    }

    public String getBusinessCode() {
        return this.businessCode;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public void setProjectIdHeader(String projectIdHeader) {
        this.projectIdHeader = projectIdHeader;
    }

    public void setProjectNoHeader(String projectNoHeader) {
        this.projectNoHeader = projectNoHeader;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setBeanPackageScan(String beanPackageScan) {
        this.beanPackageScan = beanPackageScan;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
