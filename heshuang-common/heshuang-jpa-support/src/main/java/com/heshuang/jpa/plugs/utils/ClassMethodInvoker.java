//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.utils;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.jpa.plugs.model.SqlInfo;
import org.springframework.data.repository.query.Param;

public final class ClassMethodInvoker {
    public static SqlInfo invoke(Class<?> cls, String method, Map<String, Object> paramMap) {
        Method[] methods = cls.getMethods();
        int n = methods.length;
        Method[] var5 = methods;
        int var6 = methods.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Method m = var5[var7];
            if (m.getName().equals(method)) {
                List<Object> paramValues = new ArrayList(n);
                Parameter[] var10 = m.getParameters();
                int var11 = var10.length;

                for(int var12 = 0; var12 < var11; ++var12) {
                    Parameter p = var10[var12];
                    Param param = (Param)p.getAnnotation(Param.class);
                    paramValues.add(param != null ? paramMap.get(param.value()) : null);
                }

                return invokeMethod(cls, m, paramValues);
            }
        }

        throw BusinessException.of("【Bore 异常】未找到【" + cls.getName() + "】类中可执行的公共【" + method + "】方法，请检查该方法是否存在或者访问权限是 public 型的！");
    }

    private static SqlInfo invokeMethod(Class<?> cls, Method m, List<Object> paramValues) {
        try {
            m.setAccessible(true);
            return (SqlInfo)m.invoke(cls.getDeclaredConstructor().newInstance(), paramValues.toArray());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException var4) {
            throw BusinessException.of("【Bore 异常】创建【" + cls.getName() + "】类的实例异常，请检查构造方法是否是无参 public 型的，或者检查调用的【" + m.getName() + "】方法是否是 public 型的！", var4);
        }
    }

    private ClassMethodInvoker() {
    }
}
