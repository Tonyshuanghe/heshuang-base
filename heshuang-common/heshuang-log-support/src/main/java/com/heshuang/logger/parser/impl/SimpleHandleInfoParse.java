//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.logger.conf.LoggerProperties;
import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.handler.opts.ParamOperation;
import com.heshuang.logger.parser.HandleInfoParse;
import com.heshuang.logger.parser.TemplateParse;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.List;

public class SimpleHandleInfoParse implements HandleInfoParse {
    public SimpleHandleInfoParse() {
    }
    @Override
    public List<HandlerInfo> parse(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        List<HandlerInfo> handlers = Lists.newArrayList();
        TemplateParse templateParse = loggerContext.getTemplateParse();
        MethodInvocation invocation = loggerContext.getMethodInvocation();
        OperationCache operationCache = loggerContext.getOperationCache();
        HandlerInfo handler = new HandlerInfo();
        handler.setContent(templateParse.parse(loggerOperation.getContentTemplate(), invocation));
        handler.setOperateCode(templateParse.parse(loggerOperation.getOperateCode(), invocation));
        handler.setOperationType(loggerOperation.getOperationType());
        handler.setParameters(this.parseParam(invocation, operationCache));
        handlers.add(handler);
        return handlers;
    }
    @Override
    public boolean matcher(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        return !this.isUpdate(loggerOperation) && !loggerOperation.isBatch();
    }

    protected boolean isUpdate(LoggerOperation loggerOperation) {
        return "update".equals(loggerOperation.getOperationType());
    }

    protected JSONObject parseParam(MethodInvocation invocation, OperationCache operationCache) {
        JSONObject parameters = new JSONObject();
        Object[] args = invocation.getArguments();
        Method method = invocation.getMethod();
        Parameter[] types = method.getParameters();
        int i = 0;

        for(int length = args.length; i < length; ++i) {
            if (ObjectUtils.anyNotNull(new Object[]{args[i]}) && ParamOperation.exist(types[i]) && ParamOperation.isFind(types[i]) && this.isBasePackage(args[i])) {
                this.parseChildParam(parameters, args[i], operationCache);
            }
        }

        return parameters;
    }

    protected boolean isBasePackage(Object obj) {
        return this.isBasePackage(obj.getClass());
    }

    protected boolean isBasePackage(Class clazz) {
        return clazz.getName().startsWith(((LoggerProperties) SpringUtil.getBean(LoggerProperties.class)).getBeanPackageScan());
    }

    protected void parseChildParam(JSONObject parameters, Object object, OperationCache operationCache) {
        List<ParamOperation> operations = operationCache.getParamOperation(object.getClass().getName());
        if (!CollectionUtils.isEmpty(operations)) {
            Iterator iterator = operations.iterator();

            while(true) {
                while(true) {
                    ParamOperation paramOperation;
                    Field field;
                    Object value;
                    do {
                        do {
                            if (!iterator.hasNext()) {
                                return;
                            }

                            paramOperation = (ParamOperation)iterator.next();
                        } while(!paramOperation.isParam());

                        field = paramOperation.getField();

                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException var10) {
                            throw BusinessException.of(var10.getMessage(), var10);
                        }
                    } while(!ObjectUtils.anyNotNull(new Object[]{value}));

                    if (paramOperation.isFind() && this.isBasePackage(value)) {
                        this.parseChildParam(parameters, value, operationCache);
                    } else {
                        parameters.put(StringUtils.isEmpty(paramOperation.getAlias()) ? field.getName() : paramOperation.getAlias(), value);
                    }
                }
            }
        }
    }
}
