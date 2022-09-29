//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.store;

import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.ctx.LoggerContext;
import org.aopalliance.intercept.MethodInvocation;

public interface LoggerPipeline {
    void invoke(MethodInvocation var1, JSONObject var2);

    void synchronousResource(JSONObject var1);

    void pipeline(LoggerContext var1);
}
