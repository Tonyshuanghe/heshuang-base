package com.heshuang.businessstart.mqdemo;

import com.heshuang.businessstart.mqdemo.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("rabbitmq")
@RestController
public class RabbitMsgController {
    @Autowired
    private MsgProducer sender;
 
    @GetMapping("send")
    public void sendMsg(String msg){
        sender.sendMsg(msg);
    }

    @GetMapping("sendStomp")
    public void sendStomp(String msg){
        sender.sendStomp(msg);
    }
}