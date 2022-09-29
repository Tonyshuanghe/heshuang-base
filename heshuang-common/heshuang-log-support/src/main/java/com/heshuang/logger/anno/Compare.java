//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.anno;


import com.heshuang.logger.handler.ContrastStrategy;
import com.heshuang.logger.handler.FieldParser;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Compare {
    Class<? extends FieldParser>[] parser() default {};

    boolean isScan() default false;

    String dbFieldName() default "";

    String value();

    Class<? extends ContrastStrategy>[] contrast() default {};
}
