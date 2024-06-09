package com.elice.meetstudy.domain.chatroom.handler;

import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenValidationResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
  private final TokenProvider tokenProvider;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
     if(accessor.getCommand() == StompCommand.CONNECT){
       String token = accessor.getFirstNativeHeader("Authorization");
       TokenValidationResult result = tokenProvider.validateToken(token);
          if(!result.isValid()) {
            log.info("Access Denied : {}", result.getTokenStatus().getMessage());
            throw new AccessDeniedException(result.getTokenStatus().getMessage());
          }
       Authentication authentication = tokenProvider.getAuthentication(token, result.getClaims());
       SecurityContextHolder.getContext().setAuthentication(authentication);
       log.info("AUTH SUCCESS : {}", authentication.getName());
    }
    System.out.println("accessor.getCommand() = " + accessor.getCommand());
  return message;
  }

}

