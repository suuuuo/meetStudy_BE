package com.elice.meetstudy.domain.chatroom.handler;

import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenValidationResult;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
  private final TokenProvider tokenProvider;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
    if(accessor.getCommand() == StompCommand.CONNECT){
      TokenValidationResult result = tokenProvider.validateToken(
          accessor.getFirstNativeHeader("Authorization"));
          if(!result.isValid())
        throw new AccessDeniedException(result.getTokenStatus().getMessage());
    }
  return message;
  }
}

