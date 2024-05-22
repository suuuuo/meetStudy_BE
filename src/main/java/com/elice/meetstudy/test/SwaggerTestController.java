package com.elice.meetstudy.test;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "도메인", description = "도메인 API 명세서입니다.")
public class SwaggerTestController {

  @Operation(
      summary = "API 간단한 설명",
      description = "게시글을 작성합니다."
  )
  @ApiResponse(
      responseCode = "200",
      description = "게시글 작성을 성공했습니다."
  )
  @GetMapping("/test")
  public String testSwagger() {
    return "TEST";
  }


}
