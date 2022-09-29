//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface ContentTemplateProcessor {
    String processor(List<JSONObject> var1);

    default String getFieldName(JSONObject jsonObject) {
        return jsonObject.getString("fieldName");
    }

    default String getFieldDescribe(JSONObject jsonObject) {
        return jsonObject.getString("fieldDescribe");
    }

    default String getDBFieldName(JSONObject jsonObject) {
        return jsonObject.getString("dbFieldName");
    }

    default Object getOld(JSONObject jsonObject) {
        return jsonObject.get("oldValue");
    }

    default Object getNew(JSONObject jsonObject) {
        return jsonObject.get("newValue");
    }
}
