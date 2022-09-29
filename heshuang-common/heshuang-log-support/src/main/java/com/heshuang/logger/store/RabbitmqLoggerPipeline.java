//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.heshuang.logger.store;

import com.alibaba.fastjson.JSONObject;
import com.heshuang.logger.conf.LoggerProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Iterator;
import java.util.List;

/**
 *
 * @author heshuang
 */
@Slf4j
public class RabbitmqLoggerPipeline extends AbstractLoggerPipeline {
    @Autowired
    @Qualifier("loggerRabbitTemplate")
    private RabbitTemplate rabbitTemplate;
    @Autowired
    protected LoggerProperties loggerProperties;

    public RabbitmqLoggerPipeline(RabbitTemplate rabbitTemplate, LoggerProperties loggerProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.loggerProperties = loggerProperties;
    }
    @Override
    protected void writeLogger(List<ActionLog> logs) {
        Iterator var2 = logs.iterator();

        while(var2.hasNext()) {
            ActionLog actionLog = (ActionLog)var2.next();
            this.rabbitTemplate.convertAndSend(this.loggerProperties.getTopic(), actionLog);
        }

    }
    @Override
    public void synchronousResource(JSONObject properties) {
       // TODO 业务字段,自处理(可从当前线程获取数据)
    }


}
