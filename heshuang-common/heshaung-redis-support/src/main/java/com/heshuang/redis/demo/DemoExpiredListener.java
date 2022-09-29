package com.heshuang.redis.demo;

import com.heshuang.redis.lock.IDistributedLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class DemoExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    private CacheDemo demo;

    @Autowired
    private IDistributedLock distributedLock;

    public DemoExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        //过期的key
        String key = new String(message.getBody(), StandardCharsets.UTF_8);
       log.info("监听到的key为:{}",key);
       log.info("{}",demo.getTree());
        log.info("{}",demo.getTree1());
        boolean lock = distributedLock.lock("aaa", 10000L);
        distributedLock.releaseLock("aaa");
    }
}