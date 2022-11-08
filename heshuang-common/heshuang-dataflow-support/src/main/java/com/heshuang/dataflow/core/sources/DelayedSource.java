//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.core.sources;


import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.core.base.utils.CommonUtils;
import com.heshuang.core.base.utils.PropEnvUtils;
import com.heshuang.dataflow.core.DataFilterProxy;
import com.heshuang.dataflow.core.IDataSource;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.support.DelayedTopicProcessor;
import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelayedSource implements IDataSource {
    private static final Logger log = LoggerFactory.getLogger(DelayedSource.class);
    private DelayedTopicProcessor topicPostProcessor;
    private String topic;
    private DataFilterProxy dataFilter;
    private Boolean failTry = true;
    private Class sourceType;

    public DelayedSource(DelayedTopicProcessor topicPostProcessor, String topic, Class sourceType) {
        this.topicPostProcessor = topicPostProcessor;
        this.topic = PropEnvUtils.replace(topic);
        CommonUtils.notBlank(this.topic, "topic不能空");
        this.sourceType = sourceType;
    }

    public DelayedSource(String topic, Class sourceType) {
        this.topicPostProcessor = (DelayedTopicProcessor)SpringUtil.getBean(DelayedTopicProcessor.class);
        this.topic = PropEnvUtils.replace(topic);
        CommonUtils.notBlank(this.topic, "topic不能空");
        this.sourceType = sourceType;
    }

    public DelayedSource(String topic, Class sourceType, Boolean failTry) {
        this.topicPostProcessor = (DelayedTopicProcessor)SpringUtil.getBean(DelayedTopicProcessor.class);
        this.topic = PropEnvUtils.replace(topic);
        CommonUtils.notBlank(this.topic, "topic不能空");
        this.sourceType = sourceType;
        this.failTry = failTry;
    }
    @Override
    public List on(Supplier<List<Object>> supplier) {
        if (this.dataFilter != null) {
            List list;
            if (this.failTry) {
                list = (List)supplier.get();
                DataContextHolder.getContext().setData(list);
                DataContextHolder.getContext().setCurFlowName(this.getClass().getName());
                this.dataFilter.filter(list);
            } else {
                try {
                    list = (List)supplier.get();
                    DataContextHolder.getContext().setData(list);
                    DataContextHolder.getContext().setCurFlowName(this.getClass().getName());
                    this.dataFilter.filter(list);
                } catch (Exception var4) {
                    log.warn(var4.getMessage(), var4);
                }
            }
        }

        return null;
    }
    @Override
    public void start() {
        this.topicPostProcessor.putQueue(this.topic, this);
    }
    @Override
    public void stop() {
        this.topicPostProcessor.rmQueue(this.topic);
    }
    @Override
    public void setFilter(DataFilterProxy dataFilterProxy) {
        this.dataFilter = dataFilterProxy;
    }
    @Override
    public Class sourceType() {
        return this.sourceType;
    }
}

