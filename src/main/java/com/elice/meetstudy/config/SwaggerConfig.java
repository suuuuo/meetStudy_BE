package com.elice.meetstudy.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
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
    // "/v1/**" 경로에 매칭되는 API를 그룹화하여 문서화한다.
    String[] paths = {"/**"};

    return GroupedOpenApi.builder()
        .group("MeetStudy API") // 그룹 이름 설정
        .pathsToMatch(paths) // 그룹에 속하는 경로 패턴 지정.
        .build();
  }

  @Bean
  public OpenAPI api() {
    SecurityScheme apiKey =
        new SecurityScheme()
            .type(SecurityScheme.Type.APIKEY)
            .in(SecurityScheme.In.HEADER)
            .name("Authorization");

    SecurityRequirement securityRequirement = new SecurityRequirement().addList("Bearer Token");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
        .addSecurityItem(securityRequirement);
  }
}
