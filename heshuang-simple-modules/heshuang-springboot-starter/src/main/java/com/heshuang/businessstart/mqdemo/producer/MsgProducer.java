package com.heshuang.businessstart.mqdemo.producer;

import com.heshuang.businessstart.mqdemo.MQConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MsgProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
 
    public void sendMsg(String msg) {
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE_IM_NAME,
                MQConstant.QUEUE_IM_NAME,
                msg,
                new CorrelationData()
        );
    }

    public void sendStomp(String msg) {
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend(
                "test",
                msg);
    }
}