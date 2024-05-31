package com.elice.meetstudy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenUtility {

  public Long extractUserIdFromToken(String jwtToken) {
    try {
      // 토큰 파싱을 위해 Claims 객체 생성
      Claims claims = Jwts.parserBuilder().build().parseClaimsJws(jwtToken).getBody();

      // 토큰에서 userId 추출하여 반환
      return Long.parseLong(claims.get("username", String.class));
    } catch (JwtException | NumberFormatException e) {
      // 토큰 파싱 중 에러가 발생한 경우 예외 처리
      // 예외가 발생하면 null을 반환하거나 적절한 에러 처리를 수행할 수 있습니다.
      return null;
    }
  }

  // Id를 추출
  public Long getUserIdFromToken(String jwtToken) {
    // userName을 추출하기 위한 로직 추가
    Long userId = null;
    try {
      // JWT 토큰 디코딩 및 사용자 email 추출
      userId = extractUserIdFromToken(jwtToken);
    } catch (Exception e) {
      // 예외 처리
      log.error("JWT 토큰에서 userId 추출 실패", e);
      throw new IllegalArgumentException("userId 추출 실패다..", e);
    }

    return userId;
  }
}
