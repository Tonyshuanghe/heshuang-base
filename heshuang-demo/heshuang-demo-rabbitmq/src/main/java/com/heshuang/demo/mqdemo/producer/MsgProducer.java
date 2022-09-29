package com.heshuang.demo.mqdemo.producer;

import com.google.common.collect.Lists;
import com.heshuang.demo.mqdemo.MQConstant;
import com.heshuang.dict.DictUtils;
import com.heshuang.logger.anno.Model;
import com.heshuang.logger.anno.OperateLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Model
public class MsgProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @OperateLogger(value = "#msg",operateCode = "CallActionLog")
    public void sendMsg(String msg) {
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend(MQConstant.EXCHANGE_IM_NAME,
                MQConstant.QUEUE_IM_NAME,
                msg,
                new CorrelationData()
        );
    }

    public void sendStomp(String msg) {
        log.info("发送消息：{}", msg);
        rabbitTemplate.convertAndSend(
                "test",
                msg);
    }

    public List<DictDemo> getList(String msg) {
        log.info("发送消息：{}", msg);
        ArrayList<DictDemo> list = Lists.newArrayList(new DictDemo(){{setSex("1");}},new DictDemo(){{setSex("0");}},new DictDemo(){{setSex("1");}});
        DictUtils.decode(list);
        return list;
    }
}