package com.heshuang.businessstart.mqdemo.consumer;

import com.heshuang.businessstart.mqdemo.MQConstant;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ImConsumer {
 
    //@RabbitListener(queues = MQConstant.QUEUE_IM_NAME)
    public void receiveIm(Message message,
                          Channel channel) throws IOException {
        log.info("收到即时推送消息: " + message);
        boolean ack = true;
        Exception exception = null;
        try {
            long deliveryTag = message.getMessageProperties().getDeliveryTag();
            if (deliveryTag % 2 == 1){
                throw new RuntimeException("发生异常！");
            }
        } catch (Exception e) {
            ack = false;
            exception = e;
        } finally {
 
        }
        if (!ack) {
            log.error("消息消费发生异常，error msg:{}", exception.getMessage(), exception);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
        } else {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}