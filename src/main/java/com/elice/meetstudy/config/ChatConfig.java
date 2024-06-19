package com.elice.meetstudy.config;

import com.elice.meetstudy.domain.chatroom.handler.StompHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
@RequiredArgsConstructor
public class ChatConfig implements WebSocketMessageBrokerConfigurer {

  private final StompHandler stompHandler;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    // stomp 접속 주소 url설정
    registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*");
//        .withSockJS();//Websocket를 지원하지 않는 웹브라우저의 경우 다른 방식으로 connection을 유지
   }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
  //메세지를 구독하는 요청 url
  registry.enableSimpleBroker("/room");
  //메세지를 발행하는 요청 url
    registry.setApplicationDestinationPrefixes("/send");
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(stompHandler);
  }
}
