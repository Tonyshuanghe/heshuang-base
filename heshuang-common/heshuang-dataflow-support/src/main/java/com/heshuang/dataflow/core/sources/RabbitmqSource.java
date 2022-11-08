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
import com.heshuang.dataflow.support.TopicPostProcessor;

import java.util.List;
import java.util.function.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RabbitmqSource implements IDataSource {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqSource.class);
    private TopicPostProcessor topicPostProcessor;
    private String[] topic;
    private DataFilterProxy dataFilter;
    private Boolean failTry = true;
    private Class sourceType;
    private Boolean activate = false;

    public RabbitmqSource(TopicPostProcessor topicPostProcessor, String[] topics, Class sourceType) {
        this.topicPostProcessor = topicPostProcessor;
        this.setTopic(topics);
        this.sourceType = sourceType;
    }

    private void setTopic(String[] topics) {
        this.topic = new String[topics.length];

        for(int i = 0; i < topics.length; ++i) {
            this.topic[i] = PropEnvUtils.replace(topics[i]);
            CommonUtils.notBlank(this.topic[i], "topic不能空");
        }

    }

    public RabbitmqSource(String[] topics, Class sourceType) {
        this.topicPostProcessor = (TopicPostProcessor)SpringUtil.getBean(TopicPostProcessor.class);
        this.setTopic(topics);
        this.sourceType = sourceType;
    }

    public RabbitmqSource(String[] topics, Class sourceType, Boolean failTry) {
        this.topicPostProcessor = (TopicPostProcessor)SpringUtil.getBean(TopicPostProcessor.class);
        this.setTopic(topics);
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
        this.activate = true;
        this.topicPostProcessor.putQueue(this.topic, this);
    }
    @Override
    public void stop() {
        this.activate = false;
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
    @Override
    public boolean isActivate() {
        return this.activate;
    }
}