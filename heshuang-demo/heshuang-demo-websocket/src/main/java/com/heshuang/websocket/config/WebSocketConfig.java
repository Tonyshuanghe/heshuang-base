package com.heshuang.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {
 
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        //获取请求头
        request.getHeaders().get("Sec-WebSocket-Protocol").get(0);
        //当Sec-WebSocket-Protocol请求头不为空时,需要返回给前端相同的响应
        response.getHeaders().put("Sec-WebSocket-Protocol",request.getHeaders().get(0));
        /**
         *获取请求头后的逻辑处理
         */
        super.modifyHandshake(sec, request, response);
    }
}

