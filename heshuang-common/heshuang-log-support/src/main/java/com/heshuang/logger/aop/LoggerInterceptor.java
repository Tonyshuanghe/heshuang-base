//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.aop;

import org.aopalliance.intercept.MethodInvocation;

public interface LoggerInterceptor {
    void execute(MethodInvocation var1);

    void loadOldValue(MethodInvocation var1);

    void initialize(MethodInvocation var1);
}
