//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.aop;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.utils.SingletonUtils;
import com.heshuang.logger.exception.ErrorLoggerHandler;
import com.heshuang.logger.handler.OldValueLoader;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.handler.opts.ModelOperation;
import com.heshuang.logger.parser.TemplateParse;
import com.heshuang.logger.parser.impl.SPELTemplateParse;
import com.heshuang.logger.store.LoggerPipeline;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskRejectedException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;

public class LoggerExecuteInterceptor implements LoggerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggerExecuteInterceptor.class);
    @Autowired
    private OperationCache operationCache;
    @Autowired
    private ErrorLoggerHandler errorLoggerHandler;
    private TemplateParse templateParse = (TemplateParse) SingletonUtils.getInstance(SPELTemplateParse.class);
    private final List<String> NOTES = Lists.newArrayList();
    @Autowired
    @Qualifier("operateLoggerExecutor")
    private Executor execute;
    private static ThreadLocal<Object> oldValue = new ThreadLocal();

    public LoggerExecuteInterceptor(OperationCache operationCache, ErrorLoggerHandler errorLoggerHandler, Executor execute) {
        this.operationCache = operationCache;
        this.errorLoggerHandler = errorLoggerHandler;
        this.execute = execute;
    }

    @Override
    public void execute(MethodInvocation invocation) {
        String clazzName = invocation.getThis().getClass().getName();
        ModelOperation modelOperation = this.operationCache.getModelOperation(clazzName);
        if (log.isDebugEnabled()) {
            log.debug("current model name {} or current service {}", modelOperation.getModelName(), clazzName);
        }

        try {
            if (modelOperation == null) {
                modelOperation = ModelOperation.build(invocation.getThis().getClass());
            }

            LoggerPipeline pipeline = modelOperation.getPipeline();
            JSONObject properties = new JSONObject();
            properties.put("old", oldValue.get());
            pipeline.synchronousResource(properties);
            this.execute(() -> {
                try {
                    pipeline.invoke(invocation, properties);
                } catch (Exception var5) {
                    this.errorLoggerHandler.executeError(var5);
                }

            }, modelOperation.isAsync());
        } catch (Exception var9) {
            log.error("model [name->{},service-{}] execute error", new Object[]{modelOperation.getModelName(), clazzName, var9});
        } finally {
            oldValue.remove();
        }

    }

    @Override
    public void loadOldValue(MethodInvocation invocation) {
        try {
            List<LoggerOperation> loggerOperations = this.operationCache.getLoggerOperation(invocation.getMethod().toGenericString());
            Optional<LoggerOperation> optionalOperation = loggerOperations.stream().filter((lo) -> {
                return "update".equals(lo.getOperationType());
            }).findFirst();
            if (optionalOperation.isPresent()) {
                LoggerOperation loggerOperation = (LoggerOperation)optionalOperation.get();
                OldValueLoader valueLoader = loggerOperation.getOldValueLoader();
                String valueLoaderKey = loggerOperation.getOldValueLoaderKey();
                if (ObjectUtils.anyNotNull(new Object[]{valueLoader})) {
                    oldValue.set(valueLoader.get(loggerOperation.getOperateCode(), this.templateParse.parse(valueLoaderKey, invocation)));
                }
            }
        } catch (Exception var7) {
            log.error("failure to oldValue loader", var7);
        }

    }

    @Override
    public void initialize(MethodInvocation invocation) {
        try {
            Class<?> targetClass = invocation.getThis().getClass();
            if (!this.NOTES.contains(targetClass.getName())) {
                synchronized(this.NOTES) {
                    if (!this.NOTES.contains(targetClass.getName())) {
                        this.operationCache.initialization(invocation.getThis());
                        this.NOTES.add(targetClass.getName());
                    }
                }
            }
        } catch (Exception var6) {
            log.error("failure to initialize operation Cache", var6);
        }

    }

    private void execute(Runnable runnable, boolean isAsync) {
        if (isAsync) {
            try {
                this.execute.execute(runnable);
            } catch (TaskRejectedException var4) {
                throw new BusinessException("log task queue benefits" + var4.getMessage());
            } catch (Exception var5) {
                throw new BusinessException("failure to log task execute" + var5.getMessage());
            }
        } else {
            runnable.run();
        }

    }
}
