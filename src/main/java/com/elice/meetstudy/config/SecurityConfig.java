package com.elice.meetstudy.config;

import com.elice.meetstudy.domain.user.jwt.JwtAccessDeniedHandler;
import com.elice.meetstudy.domain.user.jwt.JwtAuthenticationEntryPoint;
import com.elice.meetstudy.domain.user.jwt.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtFilter jwtFilter;
  private final String[] adminUrl = {"/api/admin/**"};
  private final String[] userUrl = {
    "/api/mypage/**",
    "/api/comment/**",
    "/api/post/**",
    "/api/question/**",
    "/api/chatroom/**",
    "/api/studyroom/**",
    "/api/calendar/**",
    "/api/calendarDetail/**",
    "/api/calendarAll",
    "api/chat/**"
  };
  private final String[] publicUrl = {
    "/",
    "/api/user/**",
    "/api/comment/public/**",
    "/api/post/public/**",
    "/api/categories/**",
    "/api/answer/public/**",
    "/api/question/public/**",
    "/api/admin/categories/public/**",
    "/ws/**",
    "/send/**",
    "/room/**"
  };
  private final String[] swaggerUrl = {
    "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/api-docs/**"
  };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        //        .cors(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .formLogin(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(
            handle ->
                handle
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                    .accessDeniedHandler(jwtAccessDeniedHandler))
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(publicUrl)
                    .permitAll()
                    .requestMatchers(swaggerUrl)
                    .permitAll()
                    .requestMatchers(adminUrl)
                    .hasAuthority("ADMIN")
                    .requestMatchers(userUrl)
                    .hasAuthority("USER")
                    .anyRequest()
                    .authenticated())
        .logout(
            logout ->
                logout
                    .logoutUrl("/api/user/logout") // 로그아웃 요청 URL
                    .logoutSuccessUrl("/login") // 로그아웃 성공 시 리디렉션 URL
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID")) // 쿠키 삭제
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
