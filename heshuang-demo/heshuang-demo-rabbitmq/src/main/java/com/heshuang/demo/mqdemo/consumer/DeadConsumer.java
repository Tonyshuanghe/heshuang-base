package com.heshuang.demo.mqdemo.consumer;

import com.heshuang.demo.mqdemo.MQConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class DeadConsumer {
    @RabbitListener(queues = MQConstant.QUEUE_DEAD_NAME)
    public void receiveDead(Message message,
                            Channel channel) throws IOException {
        log.info("收到死信消息: " + message);
        log.info("死信消息properties：{}", message.getMessageProperties());
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
