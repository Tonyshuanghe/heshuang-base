//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.ctx;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heshuang.core.base.utils.SingletonUtils;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.opts.FilterOperation;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.handler.opts.ParamOperation;
import com.heshuang.logger.parser.HandleInfoParse;
import com.heshuang.logger.parser.TemplateParse;
import com.heshuang.logger.parser.impl.ProxyHandleInfoParse;
import com.heshuang.logger.parser.impl.SPELTemplateParse;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;
import java.util.List;

public class DefaultLoggerContext implements LoggerContext {
    private OperationCache operationCache;
    private HandleInfoParse handleInfoParse;
    private TemplateParse templateParse = (TemplateParse) SingletonUtils.getInstance(SPELTemplateParse.class);
    private MethodInvocation invocation;
    private List<HandlerInfo> handlerInfo;
    private String modelName;
    private JSONObject properties;
    private Method method;
    private Object resource;

    public DefaultLoggerContext() {
    }

    @Override
    public TemplateParse getTemplateParse() {
        return this.templateParse;
    }

    @Override
    public void config(MethodInvocation invocation, JSONObject properties) {
        this.operationCache = (OperationCache) SpringUtil.getBean(OperationCache.class);
        this.handleInfoParse = (HandleInfoParse)SpringUtil.getBean(ProxyHandleInfoParse.class);
        this.invocation = invocation;
        this.properties = properties;
        this.resource = invocation.getThis();
        this.loaderProperties();
    }

    @Override
    public Method getMethod() {
        if (this.method == null) {
            this.method = this.invocation.getMethod();
        }

        return this.method;
    }

    @Override
    public MethodInvocation getMethodInvocation() {
        return this.invocation;
    }

    @Override
    public OperationCache getOperationCache() {
        return this.operationCache;
    }

    @Override
    public String getModelName() {
        if (!StringUtils.isEmpty(this.modelName)) {
            return this.modelName;
        } else {
            Class clazz = this.resource.getClass();
            String modelName = this.operationCache.getModelOperation(clazz.getName()).getModelName();
            this.modelName = StringUtils.isEmpty(modelName) ? clazz.getSimpleName() : modelName;
            return this.modelName;
        }
    }

    @Override
    public Object getThis() {
        return this.resource;
    }

    @Override
    public <T> T getProperties(String key, Class<T> clazz) {
        return this.properties.getObject(key, clazz);
    }

    @Override
    public String getProperties(String key) {
        return (String)this.getProperties(key, String.class);
    }

    @Override
    public List<HandlerInfo> handlerInfo() {
        if (!CollectionUtils.isEmpty(this.handlerInfo)) {
            return this.handlerInfo;
        } else {
            this.handlerInfo = Lists.newArrayList();
            String cacheKey = this.getMethod().toGenericString();
            FilterOperation filterOperation = this.operationCache.getFilterOperation(cacheKey);
            List<String> codes = Lists.newArrayList();
            boolean allTask = false;
            if (filterOperation != null) {
                String filterKey = filterOperation.getFilterKey();
                codes.addAll(filterOperation.getFilter().filterCode(StringUtils.isEmpty(filterKey) ? this : this.templateParse.parse(filterKey, this.invocation)));
            } else {
                allTask = true;
            }

            Iterator operationIterator = this.operationCache.getLoggerOperation(cacheKey).iterator();

            while(true) {
                LoggerOperation loggerOperation;
                do {
                    if (!operationIterator.hasNext()) {
                        return this.handlerInfo;
                    }

                    loggerOperation = (LoggerOperation)operationIterator.next();
                } while(!allTask && !codes.contains(loggerOperation.getOperateCode()));

                this.handlerInfo.addAll(this.handleInfoParse.parse(this, loggerOperation));
            }
        }
    }

    private void loaderProperties() {
        Object[] args = this.invocation.getArguments();
        Parameter[] types = this.getMethod().getParameters();
        int i = 0;

        for(int length = args.length; i < length; ++i) {
            if (ObjectUtils.anyNotNull(new Object[]{args[i]}) && ParamOperation.exist(types[i])) {
                this.properties.put(ParamOperation.alias(types[i]), args[i]);
            }
        }

    }

    public MethodInvocation getInvocation() {
        return this.invocation;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
