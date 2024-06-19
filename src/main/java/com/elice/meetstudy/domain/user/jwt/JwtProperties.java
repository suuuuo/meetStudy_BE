package com.elice.meetstudy.domain.user.jwt;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "spring.jwt")
public class JwtProperties {
    private String header;
    private String secret;
    @Value("${spring.jwt.accessTokenValidityInSeconds}")
    private Long accessTokenValidityInSeconds;
}
