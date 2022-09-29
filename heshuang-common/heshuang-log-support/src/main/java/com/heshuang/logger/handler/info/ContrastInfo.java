//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.info;


import com.heshuang.logger.handler.opts.ParamOperation;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ContrastInfo {
    public static final String OLD = "old";
    public static final String NEW = "new";
    private String dbFieldName;
    private String fieldName;
    private String fieldDescribe;
    private FieldInfo newFiledInfo;
    private FieldInfo oldFiledInfo;
    private ParamOperation paramOperation;

    public static FieldInfo build(Object value, Object parserValue) {
        return new FieldInfo(value, parserValue);
    }

    public ContrastInfo() {
    }

    public String getDbFieldName() {
        return this.dbFieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public String getFieldDescribe() {
        return this.fieldDescribe;
    }

    public FieldInfo getNewFiledInfo() {
        return this.newFiledInfo;
    }

    public FieldInfo getOldFiledInfo() {
        return this.oldFiledInfo;
    }

    public ParamOperation getParamOperation() {
        return this.paramOperation;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setFieldDescribe(String fieldDescribe) {
        this.fieldDescribe = fieldDescribe;
    }

    public void setNewFiledInfo(FieldInfo newFiledInfo) {
        this.newFiledInfo = newFiledInfo;
    }

    public void setOldFiledInfo(FieldInfo oldFiledInfo) {
        this.oldFiledInfo = oldFiledInfo;
    }

    public void setParamOperation(ParamOperation paramOperation) {
        this.paramOperation = paramOperation;
    }

    @Data
    @AllArgsConstructor
    public static class FieldInfo {
        private Object value;
        private Object parserValue;
    }
}
