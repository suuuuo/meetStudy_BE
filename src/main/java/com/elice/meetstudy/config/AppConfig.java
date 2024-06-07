package com.elice.meetstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig {

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.addExposedHeader("Set-Cookie");

    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  // 스택오버플로우(디코 링크) 참고해서 수정해보았습니다
  //  @Bean
  //  public WebMvcConfigurer corsConfigurer() {
  //    return new WebMvcConfigurer() {
  //      @Override
  //      public void addCorsMappings(CorsRegistry registry) {
  //        registry.addMapping("/api/**")
  //            .allowedOrigins("http://localhost:3000/")
  //            .allowedMethods("*")
  //            .maxAge(360);
  //      }
  //    };
  //  }
}
