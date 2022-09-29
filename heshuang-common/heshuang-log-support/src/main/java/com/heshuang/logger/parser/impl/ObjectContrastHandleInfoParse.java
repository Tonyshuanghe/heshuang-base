//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.handler.ContentTemplateProcessor;
import com.heshuang.logger.handler.ContrastStrategy;
import com.heshuang.logger.handler.FieldParser;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.info.ContrastInfo;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.info.ParserInfo;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.handler.opts.ParamOperation;
import com.heshuang.logger.parser.TemplateParse;
import com.heshuang.logger.utils.CamelNameUtils;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

public class ObjectContrastHandleInfoParse extends SimpleHandleInfoParse {
    private static final Logger log = LoggerFactory.getLogger(ObjectContrastHandleInfoParse.class);

    public ObjectContrastHandleInfoParse() {
    }
    @Override
    public List<HandlerInfo> parse(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        Object oldObj = loggerContext.getProperties("old", Object.class);
        Object newObj = loggerContext.getProperties("new", Object.class);
        MethodInvocation invocation = loggerContext.getMethodInvocation();
        OperationCache operationCache = loggerContext.getOperationCache();
        ContentTemplateProcessor contentTemplateProcessor = loggerOperation.getContentTemplateProcessor();
        JSONObject parameters = this.parseParam(invocation, operationCache);
        if (oldObj == null) {
            oldObj = parameters.getObject("old", Object.class);
        }

        this.calibrationParameter(oldObj, newObj);
        List<HandlerInfo> handlers = Lists.newArrayList();
        TemplateParse templateParse = loggerContext.getTemplateParse();
        HandlerInfo handler = new HandlerInfo();

        try {
            handler.setContrastResult(this.contrast(newObj, oldObj, loggerOperation.getContrast(), operationCache, ""));
        } catch (Exception var13) {
            throw BusinessException.of(var13.getMessage(), var13);
        }

        if (handler.getContrastResult().isEmpty()) {
            return handlers;
        } else {
            handler.setContent(templateParse.parse(loggerOperation.getContentTemplate(), invocation));
            handler.setOperateCode(templateParse.parse(loggerOperation.getOperateCode(), invocation));
            handler.setOperationType(loggerOperation.getOperationType());
            handler.setParameters(parameters);
            handler.setContentTemplateProcessor(contentTemplateProcessor);
            handlers.add(handler);
            return handlers;
        }
    }
    @Override
    public boolean  matcher(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        return this.isUpdate(loggerOperation) && !loggerOperation.isBatch();
    }

    protected void calibrationParameter(Object obj1, Object obj2) {
        if (!ObjectUtils.anyNotNull(new Object[]{obj1}) || !ObjectUtils.anyNotNull(new Object[]{obj2})) {
            throw new BusinessException("necessary parameters oldValue is null or newValue is null");
        }
    }

    protected List<JSONObject> contrast(Object newObj, Object oldObj, ContrastStrategy contrastStrategy, OperationCache operationCache, String parentDescribe) throws Exception {
        List<JSONObject> result = Lists.newArrayList();
        if (newObj != null && oldObj != null) {
            List<ParamOperation> paramOperations = operationCache.getParamOperation(newObj.getClass().getName());
            if (!oldObj.getClass().getName().equals(newObj.getClass().getName())) {
                oldObj = BeanUtil.copyProperties(oldObj, newObj.getClass());
            }

            if (!CollectionUtils.isEmpty(paramOperations)) {
                Iterator iterator = paramOperations.iterator();

                while(iterator.hasNext()) {
                    ParamOperation paramOperation = (ParamOperation)iterator.next();
                    if (paramOperation.isCompare()) {
                        Field field = paramOperation.getField();
                        Object newValue = field.get(newObj);
                        Object oldValue = field.get(oldObj);
                        String describe = paramOperation.getFieldDescribe();
                        if (paramOperation.isScan()) {
                            if (ObjectUtils.anyNotNull(new Object[]{newValue}) && ObjectUtils.anyNotNull(new Object[]{oldValue})) {
                                result.addAll(this.contrast(newValue, oldValue, contrastStrategy, operationCache, parentDescribe + describe + "-"));
                            }
                        } else {
                            ContrastInfo contrastInfo = new ContrastInfo();
                            contrastInfo.setParamOperation(paramOperation);
                            contrastInfo.setFieldDescribe(parentDescribe + describe);
                            contrastInfo.setDbFieldName(!StringUtils.isEmpty(paramOperation.getDbFieldName()) ? paramOperation.getDbFieldName() : CamelNameUtils.camel2underscore(field.getName()));
                            contrastInfo.setFieldName(field.getName());
                            FieldParser fieldParser = paramOperation.getFieldParser();
                            contrastInfo.setNewFiledInfo(ContrastInfo.build(newValue, ObjectUtils.anyNotNull(new Object[]{fieldParser}) ? fieldParser.parser(ParserInfo.build(newObj, newValue, field.getName())) : newValue));
                            contrastInfo.setOldFiledInfo(ContrastInfo.build(oldValue, ObjectUtils.allNotNull(new Object[]{fieldParser}) ? fieldParser.parser(ParserInfo.build(oldObj, oldValue, field.getName())) : oldValue));
                            List<JSONObject> contrastResults = contrastStrategy.contrast(contrastInfo);
                            if (!CollectionUtils.isEmpty(contrastResults)) {
                                result.addAll(contrastResults);
                            }
                        }
                    }
                }
            }

            return result;
        } else {
            return result;
        }
    }
}
