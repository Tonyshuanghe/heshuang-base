//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.exception;


import com.heshuang.logger.ctx.LoggerContext;

public interface ErrorLoggerHandler {
    void executeError(Exception var1);

    void pipelineError(Exception var1, LoggerContext var2);
}
