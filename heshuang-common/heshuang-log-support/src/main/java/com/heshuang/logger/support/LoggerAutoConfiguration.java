//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.support;


import com.heshuang.logger.aop.LoggerCentralInterceptor;
import com.heshuang.logger.aop.LoggerExecuteInterceptor;
import com.heshuang.logger.aop.LoggerInterceptor;
import com.heshuang.logger.conf.LoggerProperties;
import com.heshuang.logger.exception.DefaultErrorLoggerHandler;
import com.heshuang.logger.exception.ErrorLoggerHandler;
import com.heshuang.logger.handler.OperationCache;
import com.heshuang.logger.handler.opts.TypeOperationCache;
import com.heshuang.logger.parser.impl.*;
import com.heshuang.logger.store.RabbitmqLoggerPipeline;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

@Configuration
@EnableConfigurationProperties({LoggerProperties.class})
public class LoggerAutoConfiguration extends AbstractLoggerConfiguration {
    public LoggerAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean({ErrorLoggerHandler.class})
    public ErrorLoggerHandler errorLoggerHandler() {
        return new DefaultErrorLoggerHandler();
    }

    @Bean({"operateLoggerExecutor"})
    @ConditionalOnMissingBean(
        name = {"operateLoggerExecutor"}
    )
    public Executor executorService() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(18);
        executor.setMaxPoolSize(26);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("Operate-ActionLog-Thread-");
        executor.setRejectedExecutionHandler(new AbortPolicy());
        return executor;
    }

    @Bean({"loggerExecuteInterceptor"})
    public LoggerExecuteInterceptor loggerExecuteInterceptor(OperationCache operationCache, ErrorLoggerHandler errorLoggerHandler, @Qualifier("operateLoggerExecutor") Executor execute) {
        return new LoggerExecuteInterceptor(operationCache, errorLoggerHandler, execute);
    }

    @Bean({"systemLoggerAdvisor"})
    public Advisor systemLoggerAdvisor(LoggerInterceptor loggerInterceptor) {
        AspectJExpressionPointcutAdvisor pointcutAdvisor = new AspectJExpressionPointcutAdvisor();
        LoggerCentralInterceptor interceptor = new LoggerCentralInterceptor(loggerInterceptor);
        pointcutAdvisor.setExpression("@annotation(com.heshuang.logger.anno.OperateLogger) || @annotation(com.heshuang.logger.anno.OperateLoggers)");
        pointcutAdvisor.setAdvice(interceptor);
        pointcutAdvisor.setOrder((Integer)this.enableSystemLogger.getNumber("order"));
        return pointcutAdvisor;
    }

    @Bean({"typeOperationCache"})
    public TypeOperationCache typeOperationCache(LoggerProperties loggerProperties) {
        return new TypeOperationCache(loggerProperties);
    }

    @Bean({"listContrastHandleInfoParse"})
    public ListContrastHandleInfoParse listContrastHandleInfoParse() {
        return new ListContrastHandleInfoParse();
    }

    @Bean({"listHandleInfoParse"})
    public ListHandleInfoParse listHandleInfoParse() {
        return new ListHandleInfoParse();
    }

    @Bean({"objectContrastHandleInfoParse"})
    public ObjectContrastHandleInfoParse objectContrastHandleInfoParse() {
        return new ObjectContrastHandleInfoParse();
    }

    @Bean({"simpleHandleInfoParse"})
    public SimpleHandleInfoParse simpleHandleInfoParse() {
        return new SimpleHandleInfoParse();
    }

    @Bean({"proxyHandleInfoParse"})
    public ProxyHandleInfoParse proxyHandleInfoParse() {
        return new ProxyHandleInfoParse();
    }

    @Bean
    public RabbitmqLoggerPipeline rabbitmqLoggerPipeline(RabbitTemplate rabbitTemplate,LoggerProperties loggerProperties){
        return new RabbitmqLoggerPipeline(rabbitTemplate,loggerProperties);
    }

    @Bean(name = "loggerRabbitTemplate")
    public RabbitTemplate robotRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate robotRabbitTemplate = new RabbitTemplate(connectionFactory);
        robotRabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return robotRabbitTemplate;
    }
}
