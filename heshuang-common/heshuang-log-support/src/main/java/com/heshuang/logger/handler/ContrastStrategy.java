//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler;

import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.handler.info.ContrastInfo;

import java.util.List;

@FunctionalInterface
public interface ContrastStrategy {
    List<JSONObject> contrast(ContrastInfo var1) throws Exception;

    default JSONObject value(ContrastInfo contrastInfo) {
        JSONObject map = new JSONObject();
        map.put("fieldName", contrastInfo.getFieldName());
        map.put("fieldDescribe", contrastInfo.getFieldDescribe());
        map.put("dbFieldName", contrastInfo.getDbFieldName());
        map.put("oldValue", contrastInfo.getOldFiledInfo().getValue());
        map.put("newValue", contrastInfo.getNewFiledInfo().getValue());
        return map;
    }
}
