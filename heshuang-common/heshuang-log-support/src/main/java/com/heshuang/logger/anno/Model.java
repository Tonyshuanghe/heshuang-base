//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.anno;


import com.heshuang.logger.store.LoggerPipeline;
import com.heshuang.logger.store.RabbitmqLoggerPipeline;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Service
public @interface Model {
    String value() default "";

    Class<? extends LoggerPipeline>[] pipelineClass() default {RabbitmqLoggerPipeline.class};

    @AliasFor(
        annotation = Service.class,
        attribute = "value"
    )
    String beanName() default "";

    boolean isAsync() default true;
}
