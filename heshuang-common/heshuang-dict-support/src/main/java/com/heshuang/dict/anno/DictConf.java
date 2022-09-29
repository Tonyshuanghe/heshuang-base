//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict.anno;


import com.heshuang.dict.DefaultDictHandler;
import com.heshuang.dict.IDictHandler;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DictConf {
    boolean enable() default true;

    Class<? extends IDictHandler> handler() default DefaultDictHandler.class;
}
