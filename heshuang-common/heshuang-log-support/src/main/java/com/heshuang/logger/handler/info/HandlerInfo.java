//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.info;

import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.handler.ContentTemplateProcessor;
import lombok.Data;

import java.util.List;
@Data
public class HandlerInfo {
    private String content;
    private List<JSONObject> contrastResult;
    private String operateCode;
    private String operationType;
    private ContentTemplateProcessor contentTemplateProcessor;
    private JSONObject parameters;

    public <T> T getParam(String key, Class<T> clazz) {
        return this.parameters.getObject(key, clazz);
    }

    public String getParam(String key) {
        return this.parameters.getString(key);
    }

    public HandlerInfo() {
    }

    public String getContent() {
        return this.content;
    }

    public List<JSONObject> getContrastResult() {
        return this.contrastResult;
    }

    public String getOperateCode() {
        return this.operateCode;
    }

    public String getOperationType() {
        return this.operationType;
    }

    public ContentTemplateProcessor getContentTemplateProcessor() {
        return this.contentTemplateProcessor;
    }

    public JSONObject getParameters() {
        return this.parameters;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContrastResult(List<JSONObject> contrastResult) {
        this.contrastResult = contrastResult;
    }

    public void setOperateCode(String operateCode) {
        this.operateCode = operateCode;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public void setContentTemplateProcessor(ContentTemplateProcessor contentTemplateProcessor) {
        this.contentTemplateProcessor = contentTemplateProcessor;
    }

    public void setParameters(JSONObject parameters) {
        this.parameters = parameters;
    }


}
