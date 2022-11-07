//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.dataflow.support;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataFlowSupport {
    public DataFlowSupport() {
    }

    @Bean
    @ConditionalOnMissingBean({TopicPostProcessor.class})
    public TopicPostProcessor postProcessor() {
        return TopicPostProcessor.newInstance();
    }

    @Bean
    @ConditionalOnMissingBean({DirectMessageListenerContainer.class})
    public DirectMessageListenerContainer directMessageListenerContainer(ConnectionFactory connectionFactory, AmqpAdmin amqpAdmin) {
        DirectMessageListenerContainer dmlc = new DirectMessageListenerContainer();
        dmlc.setConnectionFactory(connectionFactory);
        dmlc.setConsumersPerQueue(Runtime.getRuntime().availableProcessors());
        dmlc.setAmqpAdmin(amqpAdmin);
        dmlc.setAcknowledgeMode(AcknowledgeMode.AUTO);
        dmlc.setConsumersPerQueue(1);
        dmlc.setMissingQueuesFatal(true);
        dmlc.setAutoDeclare(true);
        dmlc.setMismatchedQueuesFatal(false);
        dmlc.setAutoStartup(true);
        dmlc.setMissingQueuesFatal(false);
        return dmlc;
    }

    @Bean(
        name = {"dataFlowRabbitTemplate"}
    )
    @ConditionalOnMissingBean({RabbitTemplate.class})
    public RabbitTemplate dataFlowRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate dataFlowRabbitTemplate = new RabbitTemplate(connectionFactory);
        dataFlowRabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return dataFlowRabbitTemplate;
    }
}
