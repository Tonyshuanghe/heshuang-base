//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler;


import com.heshuang.logger.handler.opts.FilterOperation;
import com.heshuang.logger.handler.opts.LoggerOperation;
import com.heshuang.logger.handler.opts.ModelOperation;
import com.heshuang.logger.handler.opts.ParamOperation;

import java.util.List;

public interface OperationCache {
    List<LoggerOperation> getLoggerOperation(String var1);

    ModelOperation getModelOperation(String var1);

    List<ParamOperation> getParamOperation(String var1);

    FilterOperation getFilterOperation(String var1);

    void initialization(Object var1) throws Exception;
}
