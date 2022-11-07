//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.core.base.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PropEnvUtils {
    private static Pattern envPattern = Pattern.compile("\\$\\{(.*?):(.*?)\\}");

    public PropEnvUtils() {
    }

    public static String replace(String content) {
        String key;
        String def;
        String result;
        for(Iterator var1 = searchKey(content).iterator(); var1.hasNext(); content = content.replaceAll(String.format("\\$\\{%s:%s\\}", key, def), result)) {
            String[] item = (String[])var1.next();
            key = item[0];
            def = item[1];
            result = def;
            String evtValue = null;
            if (!StringUtils.isEmpty(key)) {
                evtValue = SpringUtil.getProperty(key.trim());
            }

            if (!StringUtils.isEmpty(evtValue)) {
                result = evtValue;
            }
        }

        return content;
    }

    private static List<String[]> searchKey(String str) {
        Matcher matcher = envPattern.matcher(str);
        ArrayList result = Lists.newArrayList();

        while(matcher.find()) {
            String[] item = new String[]{matcher.group(1), matcher.group(2)};
            result.add(item);
        }

        return result;
    }
}
