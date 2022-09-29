//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.handler.opts;


import com.heshuang.logger.anno.Model;
import com.heshuang.logger.handler.IOperation;
import com.heshuang.logger.store.LoggerPipeline;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Data
public class ModelOperation implements IOperation<Class<?>> {
    private static final Logger log = LoggerFactory.getLogger(ModelOperation.class);
    private String modelName;
    private LoggerPipeline pipeline;
    private boolean async;

    private ModelOperation(Class<?> clazz) throws Exception {
        this.init(clazz);
    }

    public static ModelOperation build(Class<?> clazz) throws Exception {
        return new ModelOperation(clazz);
    }
    @Override
    public void init(Class<?> clazz) throws Exception {
        Model model = (Model)clazz.getAnnotation(Model.class);
        this.modelName = model.value();
        this.async = model.isAsync();
        Class<? extends LoggerPipeline>[] classes1 = model.pipelineClass();
        if (!ArrayUtils.isEmpty(classes1)) {
            this.pipeline = (LoggerPipeline)this.getBean(classes1[0]);
        }

    }


}
