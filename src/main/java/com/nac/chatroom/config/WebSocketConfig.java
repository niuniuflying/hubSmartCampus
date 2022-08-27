package com.nac.chatroom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    //注入ServerEndpointExporter bean对象，自动注册使用了@ServerEndpoint注解的bean
    public ServerEndpointExporter ServerEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
