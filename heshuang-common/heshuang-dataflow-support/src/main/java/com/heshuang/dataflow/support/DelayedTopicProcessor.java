//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.support;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshuang.core.base.exception.BusinessException;
import com.heshuang.core.base.utils.SnowflakeUtils;
import com.heshuang.dataflow.core.IDataSource;
import com.heshuang.dataflow.core.context.DataContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.support.RootBeanDefinition;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DelayedTopicProcessor {
    private static final Logger log = LoggerFactory.getLogger(DelayedTopicProcessor.class);
    private Map<String, String[]> topicBeanCache;
    private Map<String, IDataSource> dataSource;
    private DirectMessageListenerContainer directMessageListenerContainer;
    private ObjectMapper mapper = new ObjectMapper();

    private DelayedTopicProcessor() {
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static DelayedTopicProcessor newInstance() {
        DelayedTopicProcessor topicPostProcessor = new DelayedTopicProcessor();
        topicPostProcessor.init();
        return topicPostProcessor;
    }

    public void init() {
        this.directMessageListenerContainer = (DirectMessageListenerContainer)SpringUtil.getBean(DirectMessageListenerContainer.class);
        this.topicBeanCache = Maps.newConcurrentMap();
        this.dataSource = Maps.newConcurrentMap();
        this.directMessageListenerContainer.setMessageListener((msg) -> {
            String topic = msg.getMessageProperties().getConsumerQueue();
            IDataSource ds = (IDataSource)this.dataSource.get(topic);
            if (ds != null) {
                ds.on(() -> {
                    Object msgObject = null;

                    try {
                        DataContextHolder.getContext().put("_msg-properties", msg.getMessageProperties());
                        if (ds.sourceType() != null) {
                            msgObject = this.mapper.readValue(msg.getBody(), ds.sourceType());
                        } else {
                            msgObject = new String(msg.getBody());
                        }
                    } catch (IOException var5) {
                        throw BusinessException.of(var5.getMessage(), var5);
                    }

                    return (List)(msgObject instanceof List ? (List)msgObject : Lists.newArrayList(new Object[]{msgObject}));
                });
            } else {
                log.warn("topic[{}]??????????????????", topic);
            }

        });
    }

    public void putQueue(String key, IDataSource ds) {
        this.dataSource.put(key, ds);
        this.queueIntoIoc(key);
    }

    public void rmQueue(String key) {
        this.dataSource.remove(key);
        this.queueRemoveIoc(key);
    }

    public void queueIntoIoc(String key) {
        this.queueIntoIoc(key, SnowflakeUtils.generate());
    }

    private void queueIntoIoc(String topic, long seq) {
        Map args = Maps.newHashMap();
        args.put("x-dead-letter-exchange", "inf.dle");
        Queue queue = new Queue(topic, false, false, false, args);
        DirectExchange directExchange = new DirectExchange("");
        Binding binding = BindingBuilder.bind(queue).to(directExchange).withQueueName();
        String queueName = String.format("apmq-queue-%s", seq);
        String bindName = String.format("apmq-bind-%s", seq);
        this.registBean(queue, queueName);
        this.registBean(binding, bindName);
        RabbitAdmin rabbitAdmin = (RabbitAdmin)SpringUtil.getBean(RabbitAdmin.class);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareBinding(binding);
        this.directMessageListenerContainer.addQueues(new Queue[]{queue});
        this.topicBeanCache.put(topic, new String[]{queueName, bindName});
    }

    public void queueRemoveIoc(String topic) {
        this.directMessageListenerContainer.removeQueueNames(new String[]{topic});
        if (this.topicBeanCache.containsKey(topic)) {
            this.destroyBean(((String[])this.topicBeanCache.get(topic))[0]);
            this.destroyBean(((String[])this.topicBeanCache.get(topic))[1]);
            this.topicBeanCache.remove(topic);
        }

    }

    private <T> void registBean(T buildObj, String beanName) {
        if (!BeanPostHolder.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            RootBeanDefinition bean = new RootBeanDefinition(buildObj.getClass());
            BeanPostHolder.beanDefinitionRegistry.registerBeanDefinition(beanName, bean);
        }

    }

    private void destroyBean(String beanName) {
        if (BeanPostHolder.beanDefinitionRegistry.containsBeanDefinition(beanName)) {
            BeanPostHolder.beanDefinitionRegistry.removeBeanDefinition(beanName);
        }

    }
}
