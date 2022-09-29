//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class ReflectUtils {
    public ReflectUtils() {
    }

    public static Field[] getAllFields(Class clazz) {
        return (Field[])getAllMember(clazz, (clazz1) -> {
            return Arrays.asList(clazz1.getDeclaredFields());
        }).toArray(new Field[0]);
    }

    public static Method[] getAllMethods(Class clazz) {
        return (Method[])getAllMember(clazz, (clazz1) -> {
            return Arrays.asList(clazz1.getDeclaredMethods());
        }).toArray(new Method[0]);
    }

    private static <T> List<T> getAllMember(Class clazz, ReflectUtils.Callback<T> callback) {
        List<T> fields = new ArrayList();
        fields.addAll(callback.call(clazz));
        fields.addAll(getSuperMember(clazz.getSuperclass(), callback));
        return fields;
    }

    private static <T> List<T> getSuperMember(Class superclass, ReflectUtils.Callback<T> callback) {
        List<T> list = new ArrayList();
        if (superclass != null) {
            list.addAll(callback.call(superclass));
            Class superclass1 = superclass.getSuperclass();
            if (superclass1 != null) {
                list.addAll(getSuperMember(superclass1, callback));
            }
        }

        return list;
    }

    public static Class getBeanProtoType(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Class rawType = (Class)parameterizedType.getRawType();
            if (Collection.class.isAssignableFrom(rawType)) {
                Type actualTypeArgument = parameterizedType.getActualTypeArguments()[0];
                return actualTypeArgument instanceof ParameterizedType ? getBeanProtoType(actualTypeArgument) : (Class)actualTypeArgument;
            } else {
                return rawType;
            }
        } else {
            return (Class)type;
        }
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Method test = ReflectUtils.class.getMethod("test", List.class);
        Type[] genericParameterTypes = test.getGenericParameterTypes();
        Class beanProtoType = getBeanProtoType(genericParameterTypes[0]);
        System.out.println(beanProtoType);
    }

    public void test(List<Map<String, String>> l) {
    }

    public static boolean isCollection(Type type) {
        return type instanceof ParameterizedType ? Collection.class.isAssignableFrom((Class)((ParameterizedType)type).getRawType()) : false;
    }

    interface Callback<T> {
        List<T> call(Class var1);
    }
}
