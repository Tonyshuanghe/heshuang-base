package com.heshuang.websocket.consumer;

import com.google.common.collect.Lists;
import com.heshuang.dataflow.core.IDataFilter;
import com.heshuang.websocket.consumer.handler.ForwardConsumerHandler;
import com.heshuang.dataflow.core.DataStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/3/15 11:40
 * Description: 转发消费者初始化类
 */
@Configuration
@Slf4j
public class ForwardConsumerRunner implements ApplicationRunner {

    @Value("${forwardQueue:forwardConsumerTest}")
    private String forwardQueue;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        DataStream.cron("1/5 * * * * ?", var1 -> {
            System.out.println("定时执行IDataFilter");
            return Lists.newArrayList();
        }).todo(var1 -> {
            System.out.println("定时执行IDataFilter2");
            return var1;
        }).handle();
        DataStream.mqtt("mqttTest","mqttTest.routing").todo(data->{
            log.info(String.format("%s", data));
            return data;
        }).handle();
        DataStream
                .rabbit(forwardQueue, Object.class,false)
                .todo(new ForwardConsumerHandler()).handle();
    }


}