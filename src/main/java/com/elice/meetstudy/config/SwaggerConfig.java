package com.elice.meetstudy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info =
        @Info(title = "MeetStudy API 명세서", description = "final team9의 MeetStudy 프로젝트 API 명세서입니다."))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi chatOpenApi() {
    String[] paths = {"/api/**", "/api/admin/**"};

    return GroupedOpenApi.builder().group("MeetStudy API").pathsToMatch(paths).build();
  }
}
