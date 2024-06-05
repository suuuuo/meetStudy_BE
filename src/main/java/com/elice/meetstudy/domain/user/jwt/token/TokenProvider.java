package com.elice.meetstudy.domain.user.jwt.token;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenInfo;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenValidationResult;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;


@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String TOKEN_ID_KEY = "tokenId";
    private static final String USERNAME_KEY = "username";


    private final Key hashKey;
    private final long accessToKenValidationInMilliseconds;

    public TokenProvider(String secret, long accessToKenValidationInMilliseconds){
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.hashKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessToKenValidationInMilliseconds = accessToKenValidationInMilliseconds;
    }

    public TokenInfo createToken(User user) {
        long currentTime = (new Date()).getTime();
        Date accessTokenExpireTime = new Date(currentTime + accessToKenValidationInMilliseconds);

        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(user.getId())) // 사용자의 id를 subject로 설정
                .claim(AUTHORITIES_KEY, user.getRole())
                .claim(USERNAME_KEY, user.getUsername())
                .signWith(hashKey, SignatureAlgorithm.HS512)
                .setExpiration(accessTokenExpireTime)
                .compact();

        return TokenInfo.builder()
                .ownerLoginId(user.getEmail())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }


    //토큰 검증
    public TokenValidationResult validateToken(String token) {
        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(hashKey).build().parseClaimsJws(token).getBody();
            return new TokenValidationResult(TokenStatus.TOKEN_VALID, TokenType.ACCESS, claims);
        }catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰");
            Claims claims = e.getClaims();
            return new TokenValidationResult(TokenStatus.TOKEN_VALID, TokenType.ACCESS, null);
        }catch (SecurityException | MalformedJwtException e){
            log.info("잘못된 JWT 서명");
            return new TokenValidationResult(TokenStatus.TOKEN_WRONG_SIGNATURE, null, null);
        }catch (UnsupportedJwtException e){
            log.info("지원되지 않는 JWT 서명");
            return new TokenValidationResult(TokenStatus.TOKEN_HASH_NOT_SUPPORTED, null, null);
        }catch (IllegalArgumentException e){
            log.info("잘못된 JWT 토큰");
            return new TokenValidationResult(TokenStatus.TOKEN_WRONG_SIGNATURE, null, null);
        }
    }

    public Authentication getAuthentication(String token, Claims claims) {
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map((SimpleGrantedAuthority::new))
                .collect(Collectors.toList());

        UserPrinciple principle = new UserPrinciple(claims.getSubject(), claims.get(USERNAME_KEY, String.class), authorities);

        return new UsernamePasswordAuthenticationToken(principle, token, authorities);
    }

    private TokenValidationResult getExpiredTokenValidationResult(ExpiredJwtException e) {
        Claims claims = e.getClaims();
        return new TokenValidationResult(TokenStatus.TOKEN_EXPIRED, TokenType.ACCESS, null);
    }


}
