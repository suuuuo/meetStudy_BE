package com.elice.meetstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableScheduling
public class MeetstudyApplication {

  public static void main(String[] args) {
    SpringApplication.run(MeetstudyApplication.class, args);
  }

  //  @Bean
  //  public WebMvcConfigurer corsConfigurer() { // 전역적으로 cors에러 해결법 Spring mvc에서 사용
  //    return new WebMvcConfigurer() {
  //      public void addCorsMappings(CorsRegistry registry) {
  //
  //        registry.addMapping("/**").allowedMethods("*").allowedOrigins("http://localhost:3000");
  //      }
  //    };
  //  }
}
