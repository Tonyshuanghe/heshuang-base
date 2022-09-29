package com.heshuang.demo.mqdemo;

import com.heshuang.demo.mqdemo.producer.DictDemo;
import com.heshuang.demo.mqdemo.producer.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("getList")
    public List<DictDemo> getList(String msg){
        return sender.getList(msg);
    }
}