//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggerCentralInterceptor implements MethodInterceptor {
    private LoggerInterceptor loggerInterceptor;

    public LoggerCentralInterceptor(LoggerInterceptor loggerInterceptor) {
        this.loggerInterceptor = loggerInterceptor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.loggerInterceptor.initialize(methodInvocation);
        this.loggerInterceptor.loadOldValue(methodInvocation);
        Object proceed = methodInvocation.proceed();
        this.loggerInterceptor.execute(methodInvocation);
        return proceed;
    }
}
