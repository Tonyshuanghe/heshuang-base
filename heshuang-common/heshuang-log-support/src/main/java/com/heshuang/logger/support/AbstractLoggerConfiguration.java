//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.support;

import com.heshuang.logger.anno.EnableOperateLogger;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class AbstractLoggerConfiguration implements ImportAware {
    protected AnnotationAttributes enableSystemLogger;



    @Override
    public void setImportMetadata(AnnotationMetadata annotationMetadata) {
        this.enableSystemLogger = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableOperateLogger.class.getName(), false));
    }
}
