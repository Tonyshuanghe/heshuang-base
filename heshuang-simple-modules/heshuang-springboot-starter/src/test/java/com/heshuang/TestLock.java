package com.heshuang;

import com.heshuang.businessstart.ApplicationStart;
import com.heshuang.businessstart.demo.service.impl.DemoServiceImpl;

import com.heshuang.redis.lock.IDistributedLock;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author heshuang
 * @version v1.0
 * CreationTime: - 2022/8/10 16:32
 * Description: TODO
 */
@SpringBootTest(classes = ApplicationStart.class)
public class TestLock {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private IDistributedLock distributedLock;

    @Test
    public void test(){
        rabbitTemplate.convertAndSend("doc","测试");
    }

}
