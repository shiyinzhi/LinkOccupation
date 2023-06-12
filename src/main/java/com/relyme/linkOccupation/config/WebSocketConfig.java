package com.relyme.linkOccupation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;


/**
 * springboot 运行时放开注释部分，tomcat 运行添加注释
 * @Author: dancer
 * @Date: 2019-05-09 18:42
 * @Description:
 *     启用WebSocket支持
 **/
@Configuration
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {

    }
}
