//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.parser;


import com.heshuang.logger.ctx.LoggerContext;
import com.heshuang.logger.handler.info.HandlerInfo;
import com.heshuang.logger.handler.opts.LoggerOperation;

import java.util.List;

public interface HandleInfoParse {
    List<HandlerInfo> parse(LoggerContext var1, LoggerOperation var2);

    boolean matcher(LoggerContext var1, LoggerOperation var2);
}
