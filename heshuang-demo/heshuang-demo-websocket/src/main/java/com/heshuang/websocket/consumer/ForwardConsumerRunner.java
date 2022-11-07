package com.heshuang.websocket.consumer;

import com.heshuang.websocket.consumer.handler.ForwardConsumerHandler;
import com.heshuang.dataflow.core.DataStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

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
        DataStream
                .rabbit(forwardQueue, Object.class,false)
                .todo(new ForwardConsumerHandler()).handle();
    }


}