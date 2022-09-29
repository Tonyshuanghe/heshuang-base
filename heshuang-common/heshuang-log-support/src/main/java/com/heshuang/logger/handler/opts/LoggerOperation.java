//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.opts;


import com.heshuang.logger.anno.OperateLogger;
import com.heshuang.logger.handler.*;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Data
public class LoggerOperation implements IOperation<OperateLogger> {
    public static final String OPERATE_TYPE_UPDATE = "update";
    private String operateCode;
    private String contentTemplate;
    private String operationType;
    private ContrastStrategy contrast;
    private ContentTemplateProcessor contentTemplateProcessor;
    private String oldValueLoaderKey;
    private OldValueLoader oldValueLoader;
    private boolean batch;
    private IFilter filter;

    private LoggerOperation(OperateLogger operateLogger) throws Exception {
        this.init(operateLogger);
    }
    @Override
    public void init(OperateLogger operateLogger) throws Exception {
        this.operationMapping(operateLogger);
    }

    public static List<LoggerOperation> build(Method method) throws Exception {
        List<LoggerOperation> list = new ArrayList();
        OperateLogger operateLogger = (OperateLogger)method.getAnnotation(OperateLogger.class);
        if (operateLogger != null) {
            list.add(new LoggerOperation(operateLogger));
        } else {
            OperateLogger[] operateLoggers = (OperateLogger[])method.getAnnotationsByType(OperateLogger.class);
            if (!ArrayUtils.isEmpty(operateLoggers)) {
                int i = 0;

                for(int length = operateLoggers.length; i < length; ++i) {
                    list.add(new LoggerOperation(operateLoggers[i]));
                }
            }
        }

        return list;
    }

    private void operationMapping(OperateLogger operateLogger) throws Exception {
        this.operateCode = operateLogger.operateCode();
        this.contentTemplate = operateLogger.value();
        this.operationType = operateLogger.operationType();
        this.batch = operateLogger.isBatch();
        this.oldValueLoaderKey = operateLogger.oldValueLoaderKey();
        Class<? extends OldValueLoader>[] classes = operateLogger.oldValueLoader();
        if (!ArrayUtils.isEmpty(classes)) {
            this.oldValueLoader = (OldValueLoader)this.getBean(classes[0]);
        }

        Class<? extends ContrastStrategy>[] contrasts = operateLogger.contrast();
        if (!ArrayUtils.isEmpty(contrasts)) {
            this.contrast = (ContrastStrategy)this.getBean(contrasts[0]);
        }

        Class<? extends ContentTemplateProcessor>[] templateProcessor = operateLogger.templateProcessor();
        if (!ArrayUtils.isEmpty(templateProcessor)) {
            this.contentTemplateProcessor = (ContentTemplateProcessor)this.getBean(templateProcessor[0]);
        }

    }

}
