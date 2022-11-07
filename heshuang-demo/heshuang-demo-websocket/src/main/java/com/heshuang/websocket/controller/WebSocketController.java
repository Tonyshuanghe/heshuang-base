package com.heshuang.websocket.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heshuang.websocket.dto.SessionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author heshuang03
 */
@Component
@ServerEndpoint(value = "/ws/subscribe/{token}")
@Slf4j
public class WebSocketController {

    private static Map<String, SessionDTO> sessionMap = Maps.newConcurrentMap();

    public void sendMsg(String userId, String msg) {
        sendMsgs(Lists.newArrayList(userId),msg);
    }

    public void sendMsgs(List<String> userIds, String msg) {
        sessionMap.keySet().stream().filter(k->userIds.contains(k))
                .forEach(k->{
                    try {
                        sessionMap.get(k).getSession().getBasicRemote().sendText(msg);
                    } catch (IOException e) {
                        log.error("发送websocket消息错误", e);
                    }
                });
    }


    public void sendMsgAll(String msg) {
        sessionMap.keySet().stream()
                .forEach(k->{
                    try {
                        sessionMap.get(k).getSession().getBasicRemote().sendText(msg);
                    } catch (IOException e) {
                        log.error("发送websocket消息错误", e);
                    }
                });
    }

    @OnMessage
    public void onMessage(String msg, Session session, @PathParam("token") String token) {
        log.info("收到websocket消息, msg={}, session={}", msg, session);
        if (StringUtils.isEmpty(msg)) {
            sendMsg("怎么是条空消息", session);
            return;
        }
        //TODO 分发消息
    }

    private void sendMsg(String msg, Session session) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            log.error("发送websocket消息错误, msg={}, session={}", msg, session);
            log.error("发送websocket消息错误", e);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        sessionMap.put(token, new SessionDTO("99", session));
        log.info("连接打开咯, session={}", session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("token") String token) {
        sessionMap.remove(token);
        log.info("连接关闭咯, session={}", session);
    }

    @OnError
    public void onError(Session session, Throwable error, @PathParam("token") String token) {
        sessionMap.remove(token);
        log.error("websocket发生错误, error={}", error);
    }
}