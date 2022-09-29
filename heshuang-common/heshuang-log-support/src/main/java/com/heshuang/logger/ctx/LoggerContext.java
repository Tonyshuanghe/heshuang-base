//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.ctx;

import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.parser.TemplateParse;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;
import java.util.List;

public interface LoggerContext extends Cloneable {
    void config(MethodInvocation var1, JSONObject var2) throws Exception;

    String getModelName();

    Object getThis();

    <T> T getProperties(String var1, Class<T> var2);

    String getProperties(String var1);

    List<HandlerInfo> handlerInfo();

    Method getMethod() throws Exception;

    MethodInvocation getMethodInvocation();

    OperationCache getOperationCache();

    TemplateParse getTemplateParse();
}
