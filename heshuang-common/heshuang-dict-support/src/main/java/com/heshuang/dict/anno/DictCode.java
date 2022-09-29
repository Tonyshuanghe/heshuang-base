//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict.anno;

import com.heshuang.dict.conts.SteamDataType;

import java.lang.annotation.*;

/**
 * @author heshuang
 * 类上加 {@link DictConf}
 * 查询的数据后添加  DictUtils.decode(T t); 或 DictUtils.decode(List<T> t);
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DictCode {
    /**
     * 字典枚举字典，不配置prop字段则，取注解所在字段的值做转码
     * @return
     */
    String prop() default "";

    /**
     * 同时配置code和conts先去数据字典转换，如果失败通过枚举类转换
     */
    String code() default "";

    /**
     * conts配置枚举类，通过枚举类转换，枚举类必须要有static value(Integer|Long|String)的转换码值的方法
     */
    Class[] conts() default {};

    /**
     * 时间转换
     */
    String format() default "";

    /**
     * redis中取缓存，并赋值
     */
    SteamDataType steam() default SteamDataType.NONE;

    /**
     * 前缀路径
     */
    String ossPath() default "${heshuang.ossImgPath:}";

    /**
     * 开启是否添加前缀oss
     */
    boolean oss() default false;
}
