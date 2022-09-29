//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler;

import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.handler.info.ContrastInfo;
import com.heshuang.logger.handler.opts.ParamOperation;

import java.util.ArrayList;
import java.util.List;

public class LoggerContentTplContrast implements ContrastStrategy {
    public LoggerContentTplContrast() {
    }
    @Override
    public List<JSONObject> contrast(ContrastInfo contrastInfo) throws Exception {
        ParamOperation paramOperation = contrastInfo.getParamOperation();
        ContrastInfo.FieldInfo newFiledInfo = contrastInfo.getNewFiledInfo();
        ContrastInfo.FieldInfo oldFiledInfo = contrastInfo.getOldFiledInfo();
        Object oldValue = oldFiledInfo.getValue();
        Object newValue = newFiledInfo.getValue();
        List<JSONObject> infos = new ArrayList();
        if (newValue == null && oldValue == null) {
            return null;
        } else {
            ContrastStrategy contrast = paramOperation.getContrast();
            if (contrast == null) {
                if (newValue == null && oldValue != null) {
                    infos.add(this.value(contrastInfo));
                } else if (oldValue == null && newValue != null) {
                    infos.add(this.value(contrastInfo));
                } else if (newValue instanceof String && !newValue.equals(oldValue)) {
                    infos.add(this.value(contrastInfo));
                } else if (!JSONObject.toJSONString(newValue).equals(JSONObject.toJSONString(oldValue))) {
                    infos.add(this.value(contrastInfo));
                }
            } else {
                infos.addAll(contrast.contrast(contrastInfo));
            }

            return infos;
        }
    }
}
