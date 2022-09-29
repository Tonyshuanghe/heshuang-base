//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.store;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshuang.core.base.utils.SingletonUtils;
import com.heshuang.core.base.utils.SnowflakeUtils;
import com.heshuang.logger.conf.LoggerProperties;
import com.heshuang.logger.ctx.DefaultLoggerContext;
import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.exception.ErrorLoggerHandler;
import com.heshuang.logger.handler.ContentTemplateProcessor;
import com.heshuang.logger.handler.info.HandlerInfo;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractLoggerPipeline implements LoggerPipeline, InitializingBean {
    @Autowired
    private ErrorLoggerHandler errorLoggerHandler;
    private LoggerContext context = (LoggerContext) SingletonUtils.getInstance(DefaultLoggerContext.class);
    @Autowired
    protected LoggerProperties loggerProperties;
    private List<String> extendedField;

    public AbstractLoggerPipeline() {
    }
    @Override
    public void invoke(MethodInvocation invocation, JSONObject properties) {
        try {
            LoggerContext context = (LoggerContext)ObjectUtils.clone(this.context);
            context.config(invocation, properties);
            this.pipeline(context);
        } catch (Exception var4) {
            this.errorLoggerHandler.pipelineError(var4, this.context);
        }

    }
    @Override
    public void pipeline(LoggerContext loggerContext) {
        List<ActionLog> loggers = Lists.newArrayList();
        Iterator infoIterator = loggerContext.handlerInfo().iterator();

        while(infoIterator.hasNext()) {
            HandlerInfo handlerInfo = (HandlerInfo)infoIterator.next();
            if ("update".equals(handlerInfo.getOperationType())) {
                List<JSONObject> maps = handlerInfo.getContrastResult();
                ActionLog.ActionLogBuilder auditLogBuilder = this.loggerBuilderCommon(loggerContext, handlerInfo).contentType(1);
                ContentTemplateProcessor contentTemplateProcessor = handlerInfo.getContentTemplateProcessor();
                if (contentTemplateProcessor == null) {
                    auditLogBuilder.content(JSONObject.toJSONString(maps));
                } else {
                    auditLogBuilder.content(contentTemplateProcessor.processor(maps));
                }

                loggers.add(auditLogBuilder.build());
            } else {
                loggers.add(this.loggerBuilderCommon(loggerContext, handlerInfo).contentType(0).build());
            }
        }

        if (!CollectionUtils.isEmpty(loggers)) {
            this.writeLogger(loggers);
        }

    }
    protected abstract void writeLogger(List<ActionLog> var1);

    private ActionLog.ActionLogBuilder loggerBuilderCommon(LoggerContext loggerContext, HandlerInfo handlerInfo) {
        Long operateTime = (Long)handlerInfo.getParam("operateTm", Long.class);
        if (operateTime == null) {
            operateTime = System.currentTimeMillis();
        }

        String operator = handlerInfo.getParam("operator");
        if (StringUtils.isEmpty(operator)) {
            operator = loggerContext.getProperties("operator");
        }

        String operatorId = (String)handlerInfo.getParam("operatorId", String.class);
        if (operatorId == null) {
            operatorId = (String)loggerContext.getProperties("operatorId", String.class);
        }

        String projectNo = handlerInfo.getParam("projectNo");
        if (StringUtils.isEmpty(projectNo)) {
            projectNo = loggerContext.getProperties("projectNo");
        }

        Long projectId = (Long)handlerInfo.getParam("projectId", Long.class);
        if (projectId == null) {
            projectId = (Long)loggerContext.getProperties("projectId", Long.class);
        }

        String businessCode = handlerInfo.getParam("bizCode");
        if (StringUtils.isEmpty(businessCode)) {
            businessCode = loggerContext.getProperties("bizCode");
        }

        String bizId = (String)handlerInfo.getParam("bizId", String.class);
        if (bizId == null) {
            bizId = (String)loggerContext.getProperties("bizId", String.class);
        }

        long id = SnowflakeUtils.generate();
        ActionLog.ActionLogBuilder loggerBuilder = ActionLog.builder().operateTm(operateTime).projectId(projectId).projectNo(projectNo).bizCode(businessCode).bizId(bizId).content(handlerInfo.getContent()).operateCode(handlerInfo.getOperateCode()).operator(operator).operatorId(operatorId).operator(operator).operatorIP(loggerContext.getProperties("operatorIP")).id(id);
        if (!CollectionUtils.isEmpty(this.extendedField)) {
            Map<String, Object> extended = Maps.newHashMap();
            int i = 0;

            for(int length = this.extendedField.size(); i < length; ++i) {
                String key = (String)this.extendedField.get(i);
                Object param = handlerInfo.getParam(key, Object.class);
                if (param == null) {
                    param = loggerContext.getProperties(key, Object.class);
                }

                extended.put(key, param);
            }

            loggerBuilder.extend(extended);
        }

        return loggerBuilder;
    }
    @Override
    public void afterPropertiesSet() {
        String extendedFieldStr = this.loggerProperties.getExtend();
        if (!StringUtils.isEmpty(extendedFieldStr)) {
            String[] split = extendedFieldStr.split(",");
            this.extendedField = (List)Arrays.asList(split).stream().map(String::trim).collect(Collectors.toList());
        }

    }
}
