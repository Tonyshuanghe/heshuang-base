//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.google.common.collect.Lists;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.parser.HandleInfoParse;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProxyHandleInfoParse implements HandleInfoParse, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(ProxyHandleInfoParse.class);
    private List<HandleInfoParse> handleInfoParses = Lists.newArrayList();

    public ProxyHandleInfoParse() {
    }
    @Override
    public List<HandlerInfo> parse(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        Iterator iterator = this.handleInfoParses.iterator();

        while(iterator.hasNext()) {
            HandleInfoParse infoParse = (HandleInfoParse)iterator.next();
            if (infoParse.matcher(loggerContext, loggerOperation)) {
                return infoParse.parse(loggerContext, loggerOperation);
            }
        }

        throw new BusinessException("The current log cannot match to the corresponding parser");
    }
    @Override
    public void afterPropertiesSet() {
        Map<String, HandleInfoParse> beans = SpringUtil.getApplicationContext().getBeansOfType(HandleInfoParse.class);
        if (!MapUtils.isEmpty(beans)) {
            this.handleInfoParses.addAll((Collection)beans.values().stream().filter((handleInfoParse) -> {
                return !(handleInfoParse instanceof ProxyHandleInfoParse);
            }).collect(Collectors.toList()));
        }

    }
    @Override
    public boolean matcher(LoggerContext loggerContext, LoggerOperation loggerOperation) {
        return false;
    }
}
