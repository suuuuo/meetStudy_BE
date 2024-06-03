package com.elice.meetstudy.config;

import com.elice.meetstudy.domain.user.jwt.JwtAccessDeniedHandler;
import com.elice.meetstudy.domain.user.jwt.JwtAuthenticationEntryPoint;
import com.elice.meetstudy.domain.user.jwt.JwtFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  private final String[] adminUrl = {"api/vi/admin/**"};
  private final String[] userUrl = {"api/vi/user/**"};

  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
        .cors(AbstractHttpConfigurer::disable)
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
                auth.requestMatchers("/api/vi/**")
                    .permitAll()
                    .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/swagger-ui.html")
                    .permitAll()
                    .requestMatchers("/api/vi/admin/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/vi/user/**")
                    .hasRole("USER")
                    .anyRequest()
                    .authenticated())
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /** Security 의존성 사용 : 자동으로 추가되는 기본 로그인 화면을 제거 하기 위해 우선적으로 작성함. */
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(csrf -> csrf.disable()) // 테스트를 위해 잠시 비활성화 //
        .authorizeRequests()
        .requestMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated();
    return http.build();
  }

  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String[] excludePath = {
      "/swagger-ui/index.html",
      "/swagger-ui/swagger-ui-standalone-preset.js",
      "/swagger-ui/swagger-initializer.js",
      "/swagger-ui/swagger-ui-bundle.js",
      "/swagger-ui/swagger-ui.css",
      "/swagger-ui/index.css",
      "/swagger-ui/favicon-32x32.png",
      "/swagger-ui/favicon-16x16.png",
      "/api-docs/json/swagger-config",
      "/api-docs/json"
    };
    String path = request.getRequestURI();
    return Arrays.stream(excludePath).anyMatch(path::startsWith);
  }

  //  @Bean
  //  public CorsConfigurationSource corsConfigurationSource() {
  //    CorsConfiguration config = new CorsConfiguration();
  //    config.addAllowedOrigin("http://34.47.79.59:8080");
  //    config.addAllowedMethod("*");
  //    config.addExposedHeader("Set-Cookie");
  //    config.addAllowedHeader("*");
  //    config.setAllowCredentials(true);
  //    config.addExposedHeader("access");
  //    config.setMaxAge(3600L);
  //
  //    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  //    source.registerCorsConfiguration("/**", config);
  //
  //    return source;
  //  }
}
