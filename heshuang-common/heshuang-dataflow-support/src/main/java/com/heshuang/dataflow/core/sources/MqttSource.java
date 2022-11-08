package com.heshuang.dataflow.core.sources;


import java.util.List;
import java.util.function.Supplier;

import cn.hutool.extra.spring.SpringUtil;
import com.heshuang.core.base.utils.CommonUtils;
import com.heshuang.core.base.utils.PropEnvUtils;
import com.heshuang.dataflow.core.DataFilterProxy;
import com.heshuang.dataflow.core.IDataSource;
import com.heshuang.dataflow.core.context.DataContextHolder;
import com.heshuang.dataflow.support.RoutingPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttSource implements IDataSource {
  private static final Logger log = LoggerFactory.getLogger(MqttSource.class);
  private RoutingPostProcessor topicPostProcessor;
  private String topic;
  private String routingKey;
  private DataFilterProxy dataFilter;
  private Boolean failTry = true;
  private Class sourceType;
  private Boolean activate = false;

  public MqttSource(RoutingPostProcessor topicPostProcessor, String topic, Class sourceType) {
    this.topicPostProcessor = topicPostProcessor;
    this.topic = PropEnvUtils.replace(topic);
    CommonUtils.notBlank(this.topic, "topic不能空");
    this.sourceType = sourceType;
  }

  public MqttSource(String topic, Class sourceType) {
    this.topicPostProcessor = (RoutingPostProcessor) SpringUtil.getBean(RoutingPostProcessor.class);
    this.topic = PropEnvUtils.replace(topic);
    CommonUtils.notBlank(this.topic, "topic不能空");
    this.sourceType = sourceType;
  }

  public MqttSource(String topic, Class sourceType, Boolean failTry) {
    this.topicPostProcessor = (RoutingPostProcessor)SpringUtil.getBean(RoutingPostProcessor.class);
    this.topic = PropEnvUtils.replace(topic);
    CommonUtils.notBlank(this.topic, "topic不能空");
    this.sourceType = sourceType;
    this.failTry = failTry;
  }

  public MqttSource(String topic, String routingKey, Class sourceType) {
    this.topicPostProcessor = (RoutingPostProcessor)SpringUtil.getBean(RoutingPostProcessor.class);
    this.topic = PropEnvUtils.replace(topic);
    this.routingKey = routingKey;
    CommonUtils.notBlank(this.topic, "topic不能空");
    this.sourceType = sourceType;
  }

  public MqttSource(String topic, String routingKey, Class sourceType, Boolean failTry) {
    this.topicPostProcessor = (RoutingPostProcessor)SpringUtil.getBean(RoutingPostProcessor.class);
    this.topic = PropEnvUtils.replace(topic);
    this.routingKey = routingKey;
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
    this.activate = true;
    this.topicPostProcessor.putQueue(this.topic, this.routingKey, this);
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

