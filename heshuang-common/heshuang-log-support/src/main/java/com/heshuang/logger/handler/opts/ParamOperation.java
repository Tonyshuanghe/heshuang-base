//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.opts;


import cn.hutool.core.util.ReflectUtil;
import com.heshuang.logger.anno.Compare;
import com.heshuang.logger.anno.Param;
import com.heshuang.logger.handler.ContrastStrategy;
import com.heshuang.logger.handler.FieldParser;
import com.heshuang.logger.handler.IOperation;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
@Data
public class ParamOperation implements IOperation<Field> {
    private Field field;
    private boolean find;
    private String alias;
    private FieldParser fieldParser;
    private boolean scan;
    private String dbFieldName;
    private String fieldDescribe;
    private ContrastStrategy contrast;
    private boolean compare = false;
    private boolean param = false;

    private ParamOperation(Field field) throws Exception {
        this.init(field);
    }

    public static List<ParamOperation> build(Class clazz) throws Exception {
        Field[] fields = ReflectUtil.getFieldsDirectly(clazz,false);
        List<ParamOperation> paramOperations = new ArrayList();
        if (!ArrayUtils.isEmpty(fields)) {
            int i = 0;

            for(int length = fields.length; i < length; ++i) {
                Field field = fields[i];
                if (isParamField(field)) {
                    paramOperations.add(new ParamOperation(field));
                }
            }
        }

        return paramOperations;
    }

    public static boolean exist(Parameter parameter) {
        return ObjectUtils.anyNotNull(new Object[]{parameter.getAnnotation(Param.class)});
    }

    public static boolean isFind(Parameter parameter) {
        return ((Param)parameter.getAnnotation(Param.class)).isFind();
    }

    public static String alias(Parameter parameter) {
        return ((Param)parameter.getAnnotation(Param.class)).value();
    }

    private static boolean isParamField(Field field) {
        return ObjectUtils.anyNotNull(new Object[]{field.getAnnotation(Param.class)}) || ObjectUtils.anyNotNull(new Object[]{field.getAnnotation(Compare.class)});
    }
    @Override
    public void init(Field field) throws Exception {
        Param param = (Param)field.getAnnotation(Param.class);
        this.field = field;
        this.field.setAccessible(true);
        if (ObjectUtils.anyNotNull(new Object[]{param})) {
            this.find = param.isFind();
            this.alias = param.value();
            this.param = true;
        }

        Compare compare = (Compare)field.getAnnotation(Compare.class);
        if (ObjectUtils.anyNotNull(new Object[]{compare})) {
            this.compare = true;
            this.dbFieldName = compare.dbFieldName();
            this.scan = compare.isScan();
            this.fieldDescribe = compare.value();
            this.fieldParser = ArrayUtils.isEmpty(compare.parser()) ? null : (FieldParser)this.getBean(compare.parser()[0]);
            Class<? extends ContrastStrategy>[] contrasts = compare.contrast();
            if (!ArrayUtils.isEmpty(contrasts)) {
                this.contrast = (ContrastStrategy)this.getBean(contrasts[0]);
            }
        }

    }

    public Field getField() {
        return this.field;
    }

    public boolean isFind() {
        return this.find;
    }

    public String getAlias() {
        return this.alias;
    }

    public FieldParser getFieldParser() {
        return this.fieldParser;
    }

    public boolean isScan() {
        return this.scan;
    }

    public String getDbFieldName() {
        return this.dbFieldName;
    }

    public String getFieldDescribe() {
        return this.fieldDescribe;
    }

    public ContrastStrategy getContrast() {
        return this.contrast;
    }

    public boolean isCompare() {
        return this.compare;
    }

    public boolean isParam() {
        return this.param;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setFind(boolean find) {
        this.find = find;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setFieldParser(FieldParser fieldParser) {
        this.fieldParser = fieldParser;
    }

    public void setScan(boolean scan) {
        this.scan = scan;
    }

    public void setDbFieldName(String dbFieldName) {
        this.dbFieldName = dbFieldName;
    }

    public void setFieldDescribe(String fieldDescribe) {
        this.fieldDescribe = fieldDescribe;
    }

    public void setContrast(ContrastStrategy contrast) {
        this.contrast = contrast;
    }

    public void setCompare(boolean compare) {
        this.compare = compare;
    }

    public void setParam(boolean param) {
        this.param = param;
    }
}
