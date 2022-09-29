//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.data.annotation.QueryAnnotation;

@Documented
@QueryAnnotation
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BoreQuery {
    String value() default "";

    String countQuery() default "";

    boolean nativeQuery() default false;

    Class<?> provider() default Void.class;

    String method() default "";

    String countMethod() default "";

    boolean enableDistinct() default false;
}
