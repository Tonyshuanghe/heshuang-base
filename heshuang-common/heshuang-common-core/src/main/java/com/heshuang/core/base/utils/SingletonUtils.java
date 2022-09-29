//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.objenesis.instantiator.util.ClassUtils;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SingletonUtils {
    private static final Logger log = LoggerFactory.getLogger(SingletonUtils.class);
    private static final Map<Class<?>, Object> INSTANCE_MAP = Collections.synchronizedMap(new HashMap());
    private static final Object[] EMPTY_ARGS = new Object[0];

    public SingletonUtils() {
    }

    public static <T> T getInstance(Class<? extends T> clazz) {
        return getInstance(clazz, EMPTY_ARGS);
    }

    public static <T> T getInstance(Class<? extends T> clazz, Object... args) {
        Object object = INSTANCE_MAP.get(clazz);
        if (object != null) {
            return clazz.cast(object);
        } else {
            Class<?>[] parameterTypes = new Class[args.length];

            for(int i = 0; i < args.length; ++i) {
                parameterTypes[i] = args[i].getClass();
            }

            return getInstance(clazz, parameterTypes, args);
        }
    }

    public static <T> T getInstance(Class<? extends T> clazz, Class<?>[] parameterTypes, Object[] args) {
        T object = (T)INSTANCE_MAP.get(clazz);
        if (object == null) {
            synchronized(clazz) {
                if (object == null) {
                    T var10000;
                    try {
                        if (parameterTypes == null || args == null) {
                            if (parameterTypes == null && args == null) {
                                T instance = ClassUtils.newInstance(clazz);
                                INSTANCE_MAP.put(clazz, instance);
                                object = instance;
                                return object;
                            }

                            throw new IllegalArgumentException("Two parameter arrays must be null at the same time or not NULL at the same time");
                        }

                        if (parameterTypes.length != args.length) {
                            throw new IllegalArgumentException("parameter mismatch");
                        }

                        Constructor<?> constructor = clazz.getDeclaredConstructor(parameterTypes);
                        constructor.setAccessible(true);
                        T instance = clazz.cast(constructor.newInstance(args));
                        INSTANCE_MAP.put(clazz, instance);
                        var10000 = instance;
                    } catch (Exception var8) {
                        log.error("failure to create instance ", var8);
                        return null;
                    }

                    return var10000;
                }
            }
        }

        return null;
    }
}
