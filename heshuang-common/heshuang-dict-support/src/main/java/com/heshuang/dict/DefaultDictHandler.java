//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.utils.CommonUtils;
import com.heshuang.core.base.utils.DateUtils;
import com.heshuang.core.base.utils.PropEnvUtils;
import com.heshuang.dict.anno.DictCode;
import com.heshuang.dict.dto.DictCodeProperty;
import com.heshuang.dict.dto.DictDataDto;
import com.heshuang.dict.dto.DictSteamProperty;
import com.heshuang.dict.dto.DictTypeDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DefaultDictHandler implements IDictHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultDictHandler.class);
    private List<DictTypeDto> dictTypeDtos = Lists.newArrayList();
    private Set<DictSteamProperty> steamSource;
    private String steamId;
    private Map<String, DictSteamProperty> steamIdSource = Maps.newHashMap();
    private Map<String, DictSteamProperty> steamValueSource = Maps.newHashMap();
    private static final String STEAM_TPL = "%s###%s";


    @Override
    public <T> T decode(T source, Set<DictCodeProperty> properties) {
        return source == null ? null : this.convertDict(source, properties);
    }
    @Override
    public void setSource(List<DictTypeDto> source) {
        this.dictTypeDtos = source;
    }
    @Override
    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }
    @Override
    public void setSteamSource(Set<DictSteamProperty> steamSource) {
        this.steamSource = steamSource;
        if (!CollectionUtils.isEmpty(steamSource)) {
            Iterator var2 = steamSource.iterator();

            while(var2.hasNext()) {
                DictSteamProperty steam = (DictSteamProperty)var2.next();
                this.steamIdSource.put(String.format("%s###%s", steam.getDictSteamId(), steam.getType().getCode()), steam);
                this.steamValueSource.put(String.format("%s###%s", steam.getDictSteamId(), steam.getField()), steam);
            }
        }

    }

    private <T> T convertDict(T source, Set<DictCodeProperty> properties) {
        BeanWrapper sourceBw = PropertyAccessorFactory.forBeanPropertyAccess(source);
        Object steamIdValue = null;
        if (StringUtils.isNotEmpty(this.steamId)) {
            steamIdValue = sourceBw.getPropertyValue(this.steamId);
        }

        Iterator var5 = properties.iterator();

        while(var5.hasNext()) {
            DictCodeProperty property = (DictCodeProperty)var5.next();
            DictCode dictCode = property.getDictCode();
            String fieldKey = property.getName();
            if (StringUtils.isNotBlank(dictCode.prop())) {
                fieldKey = dictCode.prop();
            }

            this.setStreamData(sourceBw, steamIdValue, fieldKey);
            Object fieldValue = sourceBw.getPropertyValue(fieldKey);
            if (!this.setDateFormat(sourceBw, property, dictCode, fieldValue)) {
                this.setOssPath(sourceBw, property, dictCode, fieldValue);
                this.setDictCode(sourceBw, property, dictCode, fieldValue);
            }
        }

        return source;
    }

    private void setOssPath(BeanWrapper sourceBw, DictCodeProperty property, DictCode dictCode, Object fieldValue) {
        if (dictCode.oss() && StringUtils.isNotBlank(dictCode.ossPath())) {
            String ossPath = PropEnvUtils.replace(dictCode.ossPath());
            if (StringUtils.isNotBlank(ossPath)) {
                if (!ossPath.endsWith("/")) {
                    ossPath = String.format("%s/", ossPath);
                }

                if (fieldValue != null && fieldValue instanceof String && StringUtils.isNotBlank((String)fieldValue)) {
                    Object newValue = fieldValue;
                    if (Pattern.matches("^\\/.*?", fieldValue.toString())) {
                        newValue = fieldValue.toString().substring(1);
                    }

                    newValue = String.format("%s%s", ossPath, newValue);
                    sourceBw.setPropertyValue(property.getName(), newValue);
                }

                if ((fieldValue instanceof List || sourceBw.getPropertyType(property.getName()).isAssignableFrom(List.class)) && !CollectionUtils.isEmpty((List)fieldValue)) {
                    List newValue = Lists.newArrayList();

                    Object nv;
                    for(Iterator var7 = ((List)fieldValue).iterator(); var7.hasNext(); newValue.add(nv)) {
                        Object fv = var7.next();
                        nv = fv;
                        if (fv != null && fv instanceof String && StringUtils.isNotBlank((String)fieldValue)) {
                            if (Pattern.matches("^\\/.*?", fv.toString())) {
                                nv = fv.toString().substring(1);
                            }

                            nv = String.format("%s%s", ossPath, nv);
                        }
                    }

                    sourceBw.setPropertyValue(property.getName(), newValue);
                }
            }
        }

    }

    private boolean setDateFormat(BeanWrapper sourceBw, DictCodeProperty property, DictCode dictCode, Object fieldValue) {
        if (StringUtils.isNotBlank(dictCode.format()) && StringUtils.isNotBlank(dictCode.prop()) && (StringUtils.isBlank(dictCode.code()) || dictCode.conts() == null || dictCode.conts().length <= 0)) {
            try {
                if (fieldValue != null && fieldValue instanceof Date) {
                    sourceBw.setPropertyValue(property.getName(), DateUtils.parseDateToStr(dictCode.format(),(Date)fieldValue));
                }

                if (fieldValue != null && fieldValue instanceof Long) {
                    sourceBw.setPropertyValue(property.getName(), DateUtils.parseDateToStr(dictCode.format(),new Date((Long)fieldValue)));
                }

                return true;
            } catch (Exception var6) {
                log.error(var6.getMessage(), var6);
            }
        }

        return false;
    }

    private void setDictCode(BeanWrapper sourceBw, DictCodeProperty property, DictCode dictCode, Object fieldValue) {
        DictDataDto dictDataDto = null;
        if (StringUtils.isNotBlank(dictCode.code())) {
            DictTypeDto dictTypeDto = null;
            List<DictTypeDto> fetchTypeList = (List)this.dictTypeDtos.stream().filter((item) -> {
                return item != null && StringUtils.isNotBlank(item.getCode());
            }).filter((item) -> {
                return dictCode.code().equals(item.getCode());
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(fetchTypeList)) {
                dictTypeDto = (DictTypeDto)fetchTypeList.get(0);
            }

            if (dictTypeDto != null && !CollectionUtils.isEmpty(dictTypeDto.getDictDataDtos())) {
                List fieldListValue;
                if (!(fieldValue instanceof List) && !sourceBw.getPropertyType(property.getName()).isAssignableFrom(List.class)) {
                    fieldListValue = (List)dictTypeDto.getDictDataDtos().stream().filter((item) -> {
                        return item != null && StringUtils.isNotBlank(String.valueOf(item.getVal()));
                    }).filter((item) -> {
                        return item.getVal().equals(String.valueOf(fieldValue));
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(fieldListValue)) {
                        dictDataDto = (DictDataDto)fieldListValue.get(0);
                    }
                } else {
                    fieldListValue = null;
                    if (!(fieldValue instanceof Long) && !(fieldValue instanceof Integer)) {
                        if (fieldValue instanceof List) {
                            fieldListValue = (List)fieldValue;
                        }
                    } else {
                        fieldListValue = CommonUtils.asList(CommonUtils.int2BinArray(Long.valueOf(String.valueOf(fieldValue))));
                    }

                    List newListValue = new ArrayList();
                    if (CommonUtils.isNotEmpty(fieldListValue)) {
                        List<DictDataDto> fetchList = (List)dictTypeDto.getDictDataDtos().stream().filter((item) -> {
                            return item != null && StringUtils.isNotBlank(String.valueOf(item.getVal()));
                        }).collect(Collectors.toList());
                        Iterator var11 = fieldListValue.iterator();

                        while(var11.hasNext()) {
                            Object value = var11.next();
                            List<DictDataDto> filterList = (List)fetchList.stream().filter((item) -> {
                                return item.getVal().equals(String.valueOf(value));
                            }).collect(Collectors.toList());
                            if (!CollectionUtils.isEmpty(filterList)) {
                                newListValue.add(((DictDataDto)filterList.get(0)).getDictDataName());
                            } else {
                                newListValue.add(value);
                            }
                        }

                        if (!CollectionUtils.isEmpty(newListValue)) {
                            dictDataDto = new DictDataDto();
                            dictDataDto.setDictDataName(newListValue);
                            dictDataDto.setVal(fieldValue);
                        }
                    }
                }
            }
        }

        if (dictDataDto == null && dictCode.conts().length > 0) {
            Class clz = dictCode.conts()[0];
            dictDataDto = this.getContsDictData(fieldValue, dictDataDto, clz, sourceBw, property.getName());
        }

        if (dictDataDto != null && dictDataDto.getDictDataName() != null) {
            sourceBw.setPropertyValue(property.getName(), dictDataDto.getDictDataName());
        }

    }

    private void setStreamData(BeanWrapper sourceBw, Object steamIdValue, String fieldKey) {
        if (StringUtils.isNotEmpty(this.steamId) && !CollectionUtils.isEmpty(this.steamSource) && steamIdValue != null) {
            DictSteamProperty dictSteamProperty = (DictSteamProperty)this.steamValueSource.get(String.format("%s###%s", steamIdValue, fieldKey));
            if (dictSteamProperty != null) {
                sourceBw.setPropertyValue(fieldKey, dictSteamProperty.getValue());
            }
        }

    }

    private DictDataDto getContsDictData(Object fieldValue, DictDataDto dictDataDto, Class clz, BeanWrapper sourceBw, String propName) {
        try {
            Method method = null;
            String name = "value";
            Object methodValue = null;
            Class valueClz = null;

            try {
                method = clz.getMethod(name, Integer.class);
                valueClz = Integer.class;
            } catch (NoSuchMethodException var16) {
                try {
                    method = clz.getMethod(name, String.class);
                    valueClz = String.class;
                } catch (NoSuchMethodException var15) {
                    try {
                        method = clz.getMethod(name, Long.class);
                        valueClz = Long.class;
                    } catch (NoSuchMethodException var14) {
                        throw BusinessException.of("未获取到static value方法", var14);
                    }
                }
            }

            if (valueClz != null) {
                try {
                    if (!(fieldValue instanceof List) && !sourceBw.getPropertyType(propName).isAssignableFrom(List.class)) {
                        if (fieldValue != null) {
                            if (valueClz == String.class) {
                                methodValue = method.invoke((Object)null, String.valueOf(fieldValue));
                            } else {
                                methodValue = method.invoke((Object)null, valueClz.getMethod("valueOf", String.class).invoke((Object)null, String.valueOf(fieldValue)));
                            }
                        }
                    } else {
                        List fieldListValue = null;
                        if (!(fieldValue instanceof Long) && !(fieldValue instanceof Integer)) {
                            if (fieldValue instanceof List) {
                                fieldListValue = (List)fieldValue;
                            }
                        } else {
                            fieldListValue = CommonUtils.asList(CommonUtils.int2BinArray(Long.valueOf(String.valueOf(fieldValue))));
                        }

                        if (!CollectionUtils.isEmpty((List)fieldValue)) {
                            Object mValue;
                            for(Iterator var11 = ((List)fieldValue).iterator(); var11.hasNext(); fieldListValue.add(mValue)) {
                                Object value = var11.next();
                                mValue = method.invoke((Object)null, valueClz.getMethod("valueOf", String.class).invoke((Object)null, String.valueOf(value)));
                                if (mValue == null) {
                                    mValue = String.valueOf(value);
                                }
                            }

                            methodValue = fieldListValue;
                        }
                    }
                } catch (NoSuchMethodException var17) {
                    log.info("字段不是基础类型[Integer，Long，String]");
                }
            }

            if (methodValue != null) {
                dictDataDto = new DictDataDto();
                dictDataDto.setVal(fieldValue);
                dictDataDto.setDictDataName(methodValue);
            }
        } catch (BusinessException var18) {
            log.info("error:{}参数没有value方法", clz.getName(), var18);
        } catch (IllegalAccessException var19) {
            log.info("error:{}参数没有value方法调用失败", clz.getName(), var19);
        } catch (InvocationTargetException var20) {
            log.info("error:{}参数没有value方法调用失败", clz.getName(), var20);
        }

        return dictDataDto;
    }
}
