package com.elice.meetstudy.domain.chatroom.handler;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenValidationResult;
import com.elice.meetstudy.util.EntityFinder;
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

  private static final String AUTHORIZATION_HEADER = "Authorization";

  private static final String BEARER_PREFIX = "Bearer ";

  private final TokenProvider tokenProvider;

  private final EntityFinder entityFinder;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {

    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    if (accessor.getCommand() == StompCommand.CONNECT) {
      String authorizationHeader = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);

      if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
        throw new AccessDeniedException("Authorization 헤더가 없거나 유효하지 않습니다.");
      }
      String token = authorizationHeader.substring(BEARER_PREFIX.length());
      TokenValidationResult result = tokenProvider.validateToken(token);

      if (!result.isValid()) {
        throw new AccessDeniedException(result.getTokenStatus().getMessage());
      }
      //user 객체 저장
      Authentication authentication = tokenProvider.getAuthentication(token, result.getClaims());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      User user = entityFinder.getUser();
      accessor.getSessionAttributes().put("user", user);

      return message;
    }

    return message;
  }

}

