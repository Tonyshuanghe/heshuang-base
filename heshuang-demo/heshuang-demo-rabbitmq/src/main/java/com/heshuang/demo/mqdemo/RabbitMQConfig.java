package com.heshuang.demo.mqdemo;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {
 
    // 声明交换器
    @Bean("imExchange")
    public FanoutExchange imExchange() {
        return new FanoutExchange(MQConstant.EXCHANGE_IM_NAME);
    }
    @Bean("deadExchange")
    public DirectExchange deadExchange() {
        return new DirectExchange(MQConstant.EXCHANGE_DEAD_NAME);
    }
 
    // 声明队列
    @Bean("imQueue")
    public Queue imQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //绑定死信队列
        args.put("x-dead-letter-exchange", MQConstant.EXCHANGE_DEAD_NAME);
        //当前队列的死信路由Key
        args.put("x-dead-letter-routing-key", MQConstant.ROUTING_KEY_DEAD_NAME);
        args.put("x-message-ttl", 20000);//设置60秒ttl
        return QueueBuilder.durable(MQConstant.QUEUE_IM_NAME).withArguments(args).build();
    }
    @Bean("deadQueue")
    public Queue deadQueue() {
        return new Queue(MQConstant.QUEUE_DEAD_NAME);
    }
 
    // 绑定关系
    @Bean
    public Binding imBinding(@Qualifier("imQueue") Queue queue,
                             @Qualifier("imExchange") FanoutExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange);
    }
    @Bean
    public Binding deadBinding(@Qualifier("deadQueue") Queue queue,
                               @Qualifier("deadExchange") DirectExchange exchange) {
        return BindingBuilder.bind(queue)
                .to(exchange)
                .with(MQConstant.ROUTING_KEY_DEAD_NAME);
    }
}