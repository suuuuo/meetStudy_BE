package com.elice.meetstudy.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TokenUtility {

  private final String secretKey =
      "dGhpcyBpcyBteSBoaWRkZW4gand0IHNlY3JldGUga2V5LCB3aGF0IGlzIHlvdXIgand0IHNlY3JldGUga2V5Pw=="; // 서명 키 설정

  public Long extractUserIdFromToken(String jwtToken) {
    try {
      // 토큰 파싱을 위한 Claims 객체
      Claims claims =
          Jwts.parserBuilder()
              .setSigningKey(secretKey) // 서명 키 설정
              .build()
              .parseClaimsJws(jwtToken)
              .getBody();

      return Long.parseLong(claims.getSubject());
    } catch (JwtException | NumberFormatException e) {
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
      log.error("JWT 토큰에서 userId 추출 실패.....", e);
      throw new IllegalArgumentException("userId 추출 실패다..", e);
    }

    return userId;
  }
}
