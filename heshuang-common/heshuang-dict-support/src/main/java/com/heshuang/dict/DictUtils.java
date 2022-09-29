//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dict;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.heshuang.dict.anno.DictCode;
import com.heshuang.dict.anno.DictConf;
import com.heshuang.dict.anno.DictSteamId;
import com.heshuang.dict.conts.SteamDataType;
import com.heshuang.dict.dto.DictCodeProperty;
import com.heshuang.dict.dto.DictSteamProperty;
import com.heshuang.dict.dto.DictTypeDto;
import com.heshuang.dict.utils.CommonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author heshuang
 */
public class DictUtils {
    private static final Logger log = LoggerFactory.getLogger(DictUtils.class);
    private static StringRedisTemplate redisTemplate;

    public DictUtils() {
    }

    public static <T> List<T> decode(List<T> source) {
        if (source != null && source.size() > 0) {
            Class type = source.get(0).getClass();
            if (!isCode(type)) {
                return source;
            } else {
                DictConf dictConf = (DictConf)type.getAnnotation(DictConf.class);
                Set<DictCodeProperty> dictCodes = getDictFieldByAnno(type, DictCode.class);
                List<DictTypeDto> dictTypeDtos = getDictTypeDtos(dictCodes);
                String fieldName = getSteamIdField(type);
                Set<DictSteamProperty> dictSteamPropertySet = null;
                if (StringUtils.isNotEmpty(fieldName)) {
                    Map<String, SteamDataType> maps = getSteamDataType(dictCodes);
                    if (maps != null && !maps.isEmpty()) {
                        dictSteamPropertySet = getSteamField(source, maps, fieldName);
                    }
                }

                for(int i = 0; i < source.size(); ++i) {
                    decodeSource(source.get(i), dictConf, dictCodes, dictTypeDtos, fieldName, dictSteamPropertySet);
                }

                return source;
            }
        } else {
            return source;
        }
    }

    public static boolean isCode(Class source) {
        DictConf dictConf = (DictConf)source.getAnnotation(DictConf.class);
        return dictConf != null && dictConf.enable();
    }

    public static <T> T decode(T source) {
        if (source == null) {
            return null;
        } else {
            Class type = source.getClass();
            if (!isCode(type)) {
                return source;
            } else {
                DictConf dictConf = (DictConf)type.getAnnotation(DictConf.class);
                Set<DictCodeProperty> dictCodes = getDictFieldByAnno(type, DictCode.class);
                String fieldName = getSteamIdField(type);
                Set<DictSteamProperty> dictSteamPropertySet = null;
                if (StringUtils.isNotEmpty(fieldName)) {
                    Map<String, SteamDataType> maps = getSteamDataType(dictCodes);
                    if (maps != null && !maps.isEmpty()) {
                        List<T> sou = new ArrayList();
                        sou.add(source);
                        dictSteamPropertySet = getSteamField(sou, maps, fieldName);
                    }
                }

                source = decodeSource(source, dictConf, dictCodes, getDictTypeDtos(dictCodes), fieldName, dictSteamPropertySet);
                return source;
            }
        }
    }

    public List<DictTypeDto> getDictTypeDtos(Class type) {
        return getDictTypeDtos(getDictFieldByAnno(type, DictCode.class));
    }

    private static List<DictTypeDto> getDictTypeDtos(Set<DictCodeProperty> dictCodes) {
        IDictSerivce service = null;

        try {
            service = (IDictSerivce) SpringUtil.getBean(IDictSerivce.class);
        } catch (Exception var4) {
            log.error("请实现com.sanrui.plugs.dict.IDictSerivce接口,进行自定义扩展");
            return null;
        }

        List<String> types = (List)dictCodes.stream().map((p) -> {
            return p.getDictCode().code();
        }).filter((p) -> {
            return StringUtils.isNotBlank(p);
        }).collect(Collectors.toList());
        List<DictTypeDto> dictDtos = null;
        if (CommonUtils.isNotEmpty(types)) {
            dictDtos = service.getByTypes(types);
        }

        return dictDtos;
    }

    private static <T> T decodeSource(T source, DictConf dictConf, Set<DictCodeProperty> dictCodes, List<DictTypeDto> dictDtos, String steamId, Set<DictSteamProperty> steamData) {
        if (dictConf != null && dictConf.enable() && dictConf.handler() != null) {
            IDictHandler dictHandler = (IDictHandler)BeanUtils.instantiateClass(dictConf.handler());
            if (dictHandler != null) {
                if (CommonUtils.isNotEmpty(dictDtos)) {
                    dictHandler.setSource(dictDtos);
                }

                if (CommonUtils.isNotEmpty(steamData)) {
                    dictHandler.setSteamSource(steamData);
                }

                if (StringUtils.isNotEmpty(steamId)) {
                    dictHandler.setSteamId(steamId);
                }

                source = dictHandler.decode(source, dictCodes);
            }
        }

        return source;
    }

    private static Set<DictCodeProperty> getDictFieldByAnno(Class sourceClz, Class anno) {
        Field[] fields = getAllFields(sourceClz);
        Set<DictCodeProperty> annos = new HashSet();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            field.setAccessible(true);
            Annotation hasAnno = field.getAnnotation(anno);
            if (hasAnno != null) {
                DictCodeProperty property = new DictCodeProperty();
                property.setDictCode((DictCode)hasAnno);
                property.setName(field.getName());
                annos.add(property);
            }
        }

        return annos;
    }

    private static Map<String, SteamDataType> getSteamDataType(Set<DictCodeProperty> dictCodeProperties) {
        Map<String, SteamDataType> map = Maps.newHashMap();
        if (CommonUtils.isNotEmpty(dictCodeProperties)) {
            dictCodeProperties.stream().forEach((item) -> {
                if (item.getDictCode().steam() != SteamDataType.NONE) {
                    map.put(item.getName(), item.getDictCode().steam());
                }

            });
        }

        return map;
    }

    private static <T> Set<DictSteamProperty> getSteamField(List<T> objects, Map<String, SteamDataType> maps, String name) {
        Set<DictSteamProperty> annos = new HashSet();
        Set<String> keys = Sets.newHashSet();
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(objects.get(0).getClass(), name);
        Method method = propertyDescriptor.getReadMethod();
        if (redisTemplate == null) {
            redisTemplate = (StringRedisTemplate)SpringUtil.getBean(StringRedisTemplate.class);
        }

        if (redisTemplate == null) {
            log.warn("未配置realDataRedisTemplate");
            return annos;
        } else {
            Iterator var7 = objects.iterator();

            while(var7.hasNext()) {
                Object item = var7.next();

                try {
                    Object value = method.invoke(item);
                    if (value != null) {
                        keys.add(value.toString());
                    }
                } catch (Exception var15) {
                    log.warn(var15.getMessage());
                }
            }

            if (CommonUtils.isNotEmpty(keys)) {
                var7 = maps.keySet().iterator();

                while(var7.hasNext()) {
                    String fkey = (String)var7.next();
                    SteamDataType dataType = (SteamDataType)maps.get(fkey);
                    List<String> keyList = (List)keys.stream().map((key) -> {
                        return String.format(dataType.getTpl(), key);
                    }).collect(Collectors.toList());
                    List<String> keysList = (List)keys.stream().collect(Collectors.toList());
                    List<String> values = redisTemplate.opsForValue().multiGet(keyList);

                    for(int i = 0; i < keyList.size(); ++i) {
                        DictSteamProperty dictSteamProperty = new DictSteamProperty();
                        dictSteamProperty.setField(fkey);
                        dictSteamProperty.setDictSteamId((String)keysList.get(i));
                        dictSteamProperty.setValue((String)values.get(i));
                        dictSteamProperty.setType(dataType);
                        annos.add(dictSteamProperty);
                    }
                }
            }

            return annos;
        }
    }

    private static String getSteamIdField(Class sourceClz) {
        Field[] fields = getAllFields(sourceClz);
        Field[] var2 = fields;
        int var3 = fields.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Field field = var2[var4];
            field.setAccessible(true);
            Annotation hasAnno = field.getAnnotation(DictSteamId.class);
            if (hasAnno != null) {
                return field.getName();
            }
        }

        return null;
    }

    private static Field[] getAllFields(Class clazz) {
        ArrayList fieldList;
        for(fieldList = new ArrayList(); clazz != null; clazz = clazz.getSuperclass()) {
            fieldList.addAll(new ArrayList(Arrays.asList(clazz.getDeclaredFields())));
        }

        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }

    private static Field[] getAllFields(Object object) {
        return getAllFields(object.getClass());
    }
}
