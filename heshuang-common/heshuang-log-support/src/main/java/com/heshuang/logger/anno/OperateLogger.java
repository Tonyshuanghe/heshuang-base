//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.anno;


import com.heshuang.logger.handler.ContentTemplateProcessor;
import com.heshuang.logger.handler.ContrastStrategy;
import com.heshuang.logger.handler.LoggerContentTplContrast;
import com.heshuang.logger.handler.OldValueLoader;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(OperateLoggers.class)
public @interface OperateLogger {
    String operateCode();

    String value() default "";

    String operationType() default "";

    boolean isBatch() default false;

    Class<? extends ContrastStrategy>[] contrast() default {LoggerContentTplContrast.class};

    Class<? extends ContentTemplateProcessor>[] templateProcessor() default {};

    Class<? extends OldValueLoader>[] oldValueLoader() default {};

    String oldValueLoaderKey() default "";
}
