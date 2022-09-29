//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.jpa.plugs.utils;

import com.google.common.collect.Lists;
import com.googlecode.aviator.AviatorEvaluator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.util.CollectionUtils;

public final class StringHelper {
    private static final Pattern BLANK_PATTERN = Pattern.compile("\\|\t|\r|\n");
    private static final Pattern EVAL_PATTERN = Pattern.compile("\\$\\{\\{(.*?)\\}\\}");
    private static final Pattern EVAL_SIMPLE_PATTERN = Pattern.compile("\\$\\{(.*?)\\}");
    private static final String PATTERN_WHERE_AND_OR_BLANK = "(?i) WHERE AND | WHERE OR ";
    private static final String WHERE = " WHERE ";
    private static final String WHERE_PREV_SPACE = " WHERE";
    private static final String XML_EXT = ".xml";
    private static final String JAVA_EXT = ".java";
    private static final String CLASS_EXT = ".class";

    public static String replaceBlank(String str) {
        Matcher m = BLANK_PATTERN.matcher(str);
        return m.replaceAll("").replaceAll("\\s{2,}", " ").trim();
    }

    public static String replaceWhereAndOr(String s) {
        s = s.replaceAll("(?i) WHERE AND | WHERE OR ", " WHERE ").replaceAll("(?i) WHERE ORDER BY ", " ORDER BY ").replaceAll("(?i) WHERE GROUP BY ", " GROUP BY ");
        if (s.endsWith(" WHERE")) {
            s = s.substring(0, s.lastIndexOf(" WHERE"));
        }

        return s;
    }

    public static boolean isEmptyObject(Object o) {
        return o == null || o instanceof String && isBlank((String)o);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String format(String pattern, Object... args) {
        return pattern == null ? "" : MessageFormatter.arrayFormat(pattern, args).getMessage();
    }

    public static String concat(Object... objs) {
        StringBuilder sb = new StringBuilder();
        Object[] var2 = objs;
        int var3 = objs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            Object o = var2[var4];
            sb.append(o == null ? "" : o.toString());
        }

        return sb.toString();
    }

    public static String fixDot(String text) {
        return text.contains(".") ? text.replace(".", "_") : text;
    }

    private static boolean isExtFile(String filePath, String ext) {
        return filePath != null && filePath.endsWith(ext);
    }

    public static boolean isXmlFile(String filePath) {
        return isExtFile(filePath, ".xml");
    }

    public static boolean isJavaFile(String filePath) {
        return isExtFile(filePath, ".java");
    }

    public static boolean isClassFile(String filePath) {
        return isExtFile(filePath, ".class");
    }

    public static String eval(String evalStr, Map ctx) {
        if (StringUtils.isBlank(evalStr)) {
            return evalStr;
        } else {
            List<String> allEval = searchEval(evalStr);
            Iterator var3;
            String eval;
            String result;
            if (!CollectionUtils.isEmpty(allEval)) {
                var3 = allEval.iterator();

                while(var3.hasNext()) {
                    eval = (String)var3.next();
                    if (StringUtils.isNotBlank(eval)) {
                        result = (String)AviatorEvaluator.execute(eval, ctx);
                        evalStr = evalStr.replace("${{" + eval + "}}", result);
                    }
                }
            }

            allEval = searchSimpleEval(evalStr);
            if (!CollectionUtils.isEmpty(allEval)) {
                var3 = allEval.iterator();

                while(var3.hasNext()) {
                    eval = (String)var3.next();
                    if (StringUtils.isNotBlank(eval)) {
                        result = (String)AviatorEvaluator.execute(eval, ctx);
                        evalStr = evalStr.replace("${" + eval + "}", result);
                    }
                }
            }

            return evalStr;
        }
    }

    private static List<String> searchEval(String str) {
        Matcher matcher = EVAL_PATTERN.matcher(str);
        ArrayList result = Lists.newArrayList();

        while(matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    private static List<String> searchSimpleEval(String str) {
        Matcher matcher = EVAL_SIMPLE_PATTERN.matcher(str);
        ArrayList result = Lists.newArrayList();

        while(matcher.find()) {
            result.add(matcher.group(1));
        }

        return result;
    }

    private StringHelper() {
    }
}
