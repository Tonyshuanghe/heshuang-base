//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler;

import cn.hutool.extra.spring.SpringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.util.Map.Entry;

public interface IOperation<T> {
    void init(T var1) throws Exception;

    default <V> V getBean(Class<V> clazz) throws Exception {
        ApplicationContext applicationContext = SpringUtil.getApplicationContext();
        String beanName = StringUtils.uncapitalize(clazz.getSimpleName());
        String[] beanNames = applicationContext.getBeanNamesForType(clazz);
        Object bean;
        if (!ArrayUtils.isEmpty(beanNames) && ArrayUtils.contains(beanNames, beanName)) {
            bean = applicationContext.getBean(StringUtils.uncapitalize(clazz.getSimpleName()), clazz);
        } else if (ArrayUtils.isEmpty(beanNames)) {
            bean = ClassUtils.newInstance(clazz);
        } else {
            bean = ((Entry)applicationContext.getBeansOfType(clazz).entrySet().iterator().next()).getValue();
        }

        return (V) bean;
    }
}
