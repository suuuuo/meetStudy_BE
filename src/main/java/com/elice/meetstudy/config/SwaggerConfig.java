package com.elice.meetstudy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "MeetStudy API 명세서",
        description = "final team9의 MeetStudy 프로젝트 API 명세서입니다.",
        version = "v1"))

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

  @Bean
  public GroupedOpenApi chatOpenApi() {
    // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
    String[] paths = {"/v1/**", "/api/user/**"};

    return GroupedOpenApi.builder()
        .group("MeetStudy API v1")  // 그룹 이름 설정
        .pathsToMatch(paths)     // 그룹에 속하는 경로 패턴 지정.
        .build();
  }
}
