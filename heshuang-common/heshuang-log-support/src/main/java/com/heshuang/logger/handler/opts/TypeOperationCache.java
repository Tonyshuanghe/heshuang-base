//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.opts;

import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshuang.logger.anno.IgnoreProp;
import com.heshuang.logger.anno.OperateLogger;
import com.heshuang.logger.conf.LoggerProperties;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.utils.ReflectUtils;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Data
public class TypeOperationCache implements OperationCache {
    private static final Logger log = LoggerFactory.getLogger(TypeOperationCache.class);
    private Map<String, List<LoggerOperation>> loggerOperations = Maps.newHashMap();
    private Map<String, ModelOperation> modelOperations = Maps.newHashMap();
    private Map<String, List<ParamOperation>> paramOperations = Maps.newHashMap();
    private Map<String, FilterOperation> filterOperations = Maps.newHashMap();
    private LoggerProperties loggerProperties;

    public TypeOperationCache(LoggerProperties loggerProperties) {
        this.loggerProperties = loggerProperties;
    }

    private void initLoggerOperations(Class clazz) throws Exception {
        Method[] methods = ReflectUtil.getMethods(clazz);
        if (!ArrayUtils.isEmpty(methods)) {
            int i = 0;

            for(int length = methods.length; i < length; ++i) {
                Method method = methods[i];
                String methodCacheKey = this.getMethodCacheKey(method);
                if (this.isCacheMethod(method)) {
                    List<LoggerOperation> operations = (List)this.loggerOperations.get(methodCacheKey);
                    if (CollectionUtils.isEmpty(operations)) {
                        operations = LoggerOperation.build(method);
                        this.loggerOperations.put(methodCacheKey, operations);
                        log.debug("initialization LoggerOperation cache {} |  {}", methodCacheKey, operations);
                    }
                }

                if (this.isCacheFilter(method)) {
                    FilterOperation filterOperation = (FilterOperation)this.filterOperations.get(methodCacheKey);
                    if (!ObjectUtils.anyNotNull(new Object[]{filterOperation})) {
                        filterOperation = FilterOperation.build(method);
                        this.filterOperations.put(methodCacheKey, filterOperation);
                        log.debug("initialization FilterOperation cache {} |  {}", methodCacheKey, filterOperation);
                    }
                }
            }
        }

    }

    private void initParamOperations(Class clazz) throws Exception {
        Method[] methods = ReflectUtil.getMethods(clazz);
        if (!ArrayUtils.isEmpty(methods)) {
            int i = 0;

            for(int length = methods.length; i < length; ++i) {
                Method method = methods[i];
                Type[] types = method.getGenericParameterTypes();
                if (!ArrayUtils.isEmpty(types)) {
                    int j = 0;

                    for(int lengthJ = types.length; j < lengthJ; ++j) {
                        Class type = null;

                        try {
                            type = ReflectUtils.getBeanProtoType(types[j]);
                        } catch (Exception var12) {
                            log.warn("initialization , 方法{}参数中有无法识别的类型", method.toGenericString());
                            continue;
                        }

                        if (type.getName().startsWith(this.loggerProperties.getBeanPackageScan())) {
                            String cacheKey = this.getParamCacheKey(type);
                            List<ParamOperation> paramOperations = (List)this.paramOperations.get(cacheKey);
                            if (CollectionUtils.isEmpty(paramOperations)) {
                                paramOperations = ParamOperation.build(type);
                                this.paramOperations.put(cacheKey, paramOperations);
                                log.debug("initialization ParamOperation cache {} |  {}", cacheKey, paramOperations);
                            }
                        }
                    }
                }
            }
        }

    }

    private void initModelOperations(Class clazz) throws Exception {
        String typeCacheKey = this.getTypeCacheKey(clazz);
        ModelOperation modelOperation = (ModelOperation)this.modelOperations.get(typeCacheKey);
        if (!ObjectUtils.anyNotNull(new Object[]{modelOperation})) {
            modelOperation = ModelOperation.build(clazz);
            this.modelOperations.put(typeCacheKey, modelOperation);
            log.debug("initialization ModelOperation cache {} |  {} ", typeCacheKey, modelOperation);
        }

    }

    private boolean isCacheFilter(Method method) {
        return ObjectUtils.anyNotNull(new Object[]{method.getAnnotation(IgnoreProp.class)});
    }

    private String getParamCacheKey(Class clazz) {
        return clazz.getName();
    }

    private String getTypeCacheKey(Class clazz) {
        return clazz.getName();
    }

    private String getMethodCacheKey(Method method) {
        return method.toGenericString();
    }
    @Override
    public List<LoggerOperation> getLoggerOperation(String key) {
        return (List)this.loggerOperations.get(key);
    }
    @Override
    public ModelOperation getModelOperation(String key) {
        return (ModelOperation)this.modelOperations.get(key);
    }
    @Override
    public List<ParamOperation> getParamOperation(String key) {
        List<ParamOperation> paramOperations = (List)this.paramOperations.get(key);
        if (paramOperations != null) {
            return paramOperations;
        } else {
            synchronized(this) {
                if (!this.paramOperations.containsKey(key)) {
                    try {
                        Class<?> clazz = Class.forName(key);
                        if (clazz.getName().startsWith(this.loggerProperties.getBeanPackageScan())) {
                            String cacheKey = this.getParamCacheKey(clazz);
                            paramOperations = ParamOperation.build(clazz);
                            this.paramOperations.put(cacheKey, paramOperations);
                            log.debug("initialization ParamOperation cache {} |  {}", cacheKey, paramOperations);
                        }
                    } catch (Exception var7) {
                        log.error("", var7);
                    }
                }
            }

            return (List)(paramOperations == null ? Lists.newArrayList() : paramOperations);
        }
    }
    @Override
    public FilterOperation getFilterOperation(String key) {
        return (FilterOperation)this.filterOperations.get(key);
    }
    @Override
    public void initialization(Object target) throws Exception {
        Class<?> targetClass = target.getClass();
        this.initLoggerOperations(targetClass);
        this.initModelOperations(targetClass);
        this.initParamOperations(targetClass);
    }

    private boolean isCacheMethod(Method method) {
        return ObjectUtils.anyNotNull(new Object[]{method.getAnnotation(OperateLogger.class)}) || !ArrayUtils.isEmpty(method.getAnnotationsByType(OperateLogger.class));
    }


}
