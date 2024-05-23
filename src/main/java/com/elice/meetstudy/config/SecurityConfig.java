package com.elice.meetstudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        // 해수함수를 이용하여 비밀번호를 암호화하여 저장.
        return new BCryptPasswordEncoder();

    }

    /**
     * Security 의존성 사용 : 자동으로 추가되는 기본 로그인 화면을 제거 하기 위해 우선적으로 작성함.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers("/**").permitAll()
            .anyRequest().authenticated();
        return http.build();
    }
}
