package com.elice.meetstudy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfig {

  // 수민님 수정내용 참고하여 다시 수정
  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();

    config.setAllowCredentials(true);
    config.addAllowedOriginPattern("*");
    //    config.addAllowedOrigin("*");
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    config.addExposedHeader("Set-Cookie");

    source.registerCorsConfiguration("/**", config);
    return new CorsFilter(source);
  }

  //  //   스택오버플로우(디코 링크) 참고해서 수정해보았습니다
  //  @Bean
  //  public WebMvcConfigurer corsConfigurer() {
  //    return new WebMvcConfigurer() {
  //      @Override
  //      public void addCorsMappings(CorsRegistry registry) {
  //        registry.addMapping("/api/**").allowedOrigins("*").allowedMethods("*").maxAge(360);
  //      }
  //    };
  //  }
}
