//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.handler.opts.ParamOperation;
import com.heshuang.logger.parser.TemplateParse;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Parameter;
import java.util.List;

public class ListHandleInfoParse extends SimpleHandleInfoParse {
    public ListHandleInfoParse() {
    }

    public List<HandlerInfo> parse(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        List<HandlerInfo> handlers = Lists.newArrayList();
        TemplateParse templateParse = loggerContext.getTemplateParse();
        MethodInvocation invocation = loggerContext.getMethodInvocation();
        OperationCache operationCache = loggerContext.getOperationCache();
        JSONObject commonParameters = this.parseParam(invocation, operationCache);
        List list = this.getList(invocation);

        for(int i = 0; i < this.getList(invocation).size(); ++i) {
            HandlerInfo handler = new HandlerInfo();
            handler.setContent(templateParse.parse(templateParse.perParse(loggerOperation.getContentTemplate(), i), invocation));
            handler.setOperateCode(templateParse.parse(templateParse.perParse(loggerOperation.getOperateCode(), i), invocation));
            handler.setOperationType(loggerOperation.getOperationType());
            JSONObject parameters = new JSONObject();
            parameters.putAll(commonParameters);
            this.parseChildParam(parameters, list.get(i), operationCache);
            handler.setParameters(parameters);
            handlers.add(handler);
        }

        return handlers;
    }

    public boolean matcher(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        return !this.isUpdate(loggerOperation) && loggerOperation.isBatch();
    }

    private List getList(MethodInvocation invocation) {
        Object[] args = invocation.getArguments();
        Parameter[] types = invocation.getMethod().getParameters();
        int i = 0;

        for(int length = args.length; i < length; ++i) {
            if (ObjectUtils.anyNotNull(new Object[]{args[i]}) && ParamOperation.exist(types[i]) && ParamOperation.isFind(types[i]) && args[i] instanceof List && !CollectionUtils.isEmpty((List)args[i]) && this.isBasePackage(((List)args[i]).get(0))) {
                return (List)args[i];
            }
        }

        return Lists.newArrayList();
    }
}
