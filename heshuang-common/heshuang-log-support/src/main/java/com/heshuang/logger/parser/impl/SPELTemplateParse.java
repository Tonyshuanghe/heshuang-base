//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser.impl;


import com.heshuang.logger.parser.TemplateParse;
import com.heshuang.logger.utils.SpringELUtils;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class SPELTemplateParse implements TemplateParse {
    private static final Logger log = LoggerFactory.getLogger(SPELTemplateParse.class);
    private static final String INDEX_PLACEHOLDER = "\\{index\\}";

    public SPELTemplateParse() {
    }
    @Override
    public String parse(String template, MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();
        Object[] args = methodInvocation.getArguments();

        try {
            return SpringELUtils.parse(template, method, args);
        } catch (Exception var6) {
            log.warn("spring EL format error for {}", template);
            return template;
        }
    }
    @Override
    public String perParse(String template, Object obj) {
        return template.replaceAll("\\{index\\}", String.valueOf(obj));
    }
}
