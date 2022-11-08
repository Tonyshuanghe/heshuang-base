//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

import com.heshuang.core.base.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class DelayedMessenger {
    private static final Logger log = LoggerFactory.getLogger(DelayedMessenger.class);

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> void push(T data, Duration d) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("json");
        RabbitTemplate rabbitTemplate = (RabbitTemplate)SpringUtil.getBean(RabbitTemplate.class);
        if (rabbitTemplate != null) {
            try {
                Message message = new Message(mapper.writeValueAsBytes(data), messageProperties);
                messageProperties.setContentType("json");
                messageProperties.setExpiration(String.format("%s", new Object[] { Long.valueOf(d.toMillis()) }));
                rabbitTemplate.send("dle.delayed", message);
            } catch (JsonProcessingException e) {
                throw BusinessException.of(e.getMessage(), e);
            }
        } else {
            log.warn("RabbitTemplate ");
        }
    }
}