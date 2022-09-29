//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.handler.ContentTemplateProcessor;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.parser.TemplateParse;
import org.aopalliance.intercept.MethodInvocation;

import java.util.List;

public class ListContrastHandleInfoParse extends ObjectContrastHandleInfoParse {
    public ListContrastHandleInfoParse() {
    }

    @Override
    public List<HandlerInfo> parse(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        List<HandlerInfo> handlers = Lists.newArrayList();
        TemplateParse templateParse = loggerContext.getTemplateParse();
        MethodInvocation invocation = loggerContext.getMethodInvocation();
        OperationCache operationCache = loggerContext.getOperationCache();
        ContentTemplateProcessor contentTemplateProcessor = loggerOperation.getContentTemplateProcessor();
        Object value = loggerContext.getProperties("old", Object.class);
        List newObj = (List)loggerContext.getProperties("new", List.class);
        this.calibrationParameter(value, newObj);
        if (!(value instanceof List)) {
            throw new BusinessException("Batch update values are not collections");
        } else {
            List oldObj = (List)value;
            JSONObject commonParameters = this.parseParam(invocation, operationCache);

            for(int i = 0; i < oldObj.size(); ++i) {
                HandlerInfo handler = new HandlerInfo();

                try {
                    handler.setContrastResult(this.contrast(newObj.get(i), oldObj.get(i), loggerOperation.getContrast(), operationCache, ""));
                } catch (Exception var15) {
                    throw BusinessException.of(var15.getMessage(), var15);
                }

                if (!handler.getContrastResult().isEmpty()) {
                    handler.setContent(templateParse.parse(templateParse.perParse(loggerOperation.getContentTemplate(), i), invocation));
                    handler.setOperateCode(templateParse.parse(templateParse.perParse(loggerOperation.getOperateCode(), i), invocation));
                    handler.setOperationType(loggerOperation.getOperationType());
                    JSONObject parameters = new JSONObject();
                    parameters.putAll(commonParameters);
                    this.parseChildParam(parameters, newObj.get(i), operationCache);
                    handler.setParameters(parameters);
                    handler.setContentTemplateProcessor(contentTemplateProcessor);
                    handlers.add(handler);
                }
            }

            return handlers;
        }
    }

    public boolean matcher(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        return this.isUpdate(loggerOperation) && loggerOperation.isBatch();
    }
}
