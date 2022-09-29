//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.exception;
import com.heshuang.logger.ctx.LoggerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultErrorLoggerHandler implements ErrorLoggerHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultErrorLoggerHandler.class);

    public DefaultErrorLoggerHandler() {
    }

    @Override
    public void executeError(Exception e) {
        log.error("Log Component Initialization error", e);
    }

    @Override
    public void pipelineError(Exception e, LoggerContext context) {
        log.error("Pipeline Execution exception", e);
    }
}
