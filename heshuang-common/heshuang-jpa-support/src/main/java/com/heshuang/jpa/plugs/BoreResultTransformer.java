//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.heshuang.core.base.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.BlobType;
import org.hibernate.type.descriptor.java.DataHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.convert.JodaTimeConverters;
import org.springframework.util.Assert;

public class BoreResultTransformer<T> implements ResultTransformer {
    private static final long serialVersionUID = 4519223959994503529L;
    private static final DefaultConversionService conversionService = new DefaultConversionService();
    private final Class<T> resultClass;
    private final transient Map<String, PropertyDescriptor> fieldMap;

    public BoreResultTransformer(Class<T> resultClass) {
        Assert.notNull(resultClass, "【Bore 异常】resultClass cannot be null.");
        this.resultClass = resultClass;
        this.fieldMap = new HashMap();
        PropertyDescriptor[] propDescriptors = BeanUtils.getPropertyDescriptors(this.resultClass);
        PropertyDescriptor[] var3 = propDescriptors;
        int var4 = propDescriptors.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor propDescriptor = var3[var5];
            this.fieldMap.put(propDescriptor.getName().toLowerCase(), propDescriptor);
        }

    }

    public Object transformTuple(Object[] tuple, String[] aliases) {
        Object resultObject;
        try {
            resultObject = this.resultClass.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException var11) {
            throw BusinessException.of("实例化【" + this.resultClass + "】类出错，请检查该类是否包含可公开访问的无参构造方法！", var11);
        }

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(resultObject);
        beanWrapper.setConversionService(conversionService);
        int i = 0;

        for(int len = aliases.length; i < len; ++i) {
            String column = aliases[i];
            if (StringUtils.isBlank(column)) {
                throw BusinessException.of("【Bore 异常】要映射为【" + this.resultClass + "】实体的查询结果列为空，请检查并保证每一个查询结果列都必须用【as】后加“别名”的方式！");
            }

            PropertyDescriptor propDescriptor = (PropertyDescriptor)this.fieldMap.get(column.replaceAll(" ", "").toLowerCase());
            if (propDescriptor != null) {
                try {
                    beanWrapper.setPropertyValue(propDescriptor.getName(), tuple[i]);
                } catch (TypeMismatchException | NotWritablePropertyException var10) {
                    throw BusinessException.of("【Bore 异常】设置字段【" + column + "】的值到属性【" + propDescriptor.getName() + "】中出错，请检查该字段或属性是否存在或者可公开访问！", var10);
                }
            }
        }

        return resultObject;
    }

    public List<?> transformList(List list) {
        return list;
    }

    static {
        Collection<Converter<?, ?>> convertersToRegister = JodaTimeConverters.getConvertersToRegister();
        Iterator var1 = convertersToRegister.iterator();

        while(var1.hasNext()) {
            Converter<?, ?> converter = (Converter)var1.next();
            conversionService.addConverter(converter);
        }

        conversionService.addConverter(ClobToStringConverter.INSTANCE);
        conversionService.addConverter(BlobToStringConverter.INSTANCE);
    }

    private static enum ClobToStringConverter implements Converter<Clob, String> {
        INSTANCE;

        private ClobToStringConverter() {
        }

        public String convert(Clob source) {
            return DataHelper.extractString(source);
        }
    }

    private static enum BlobToStringConverter implements Converter<Blob, String> {
        INSTANCE;

        private BlobToStringConverter() {
        }

        public String convert(Blob source) {
            return BlobType.INSTANCE.toString(source);
        }
    }
}
