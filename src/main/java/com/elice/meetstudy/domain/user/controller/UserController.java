package com.elice.meetstudy.domain.user.controller;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.dto.UserLoginDto;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenInfo;
import com.elice.meetstudy.domain.user.jwt.web.json.ApiResponseJson;
import com.elice.meetstudy.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "유저", description = "유저 접속 관련 API 입니다.")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입")
  @PostMapping("/join")
  public ApiResponseJson join(@RequestBody @Valid UserJoinDto userJoinDto){

    User user = userService.join(userJoinDto);
    return new ApiResponseJson(HttpStatus.OK, Map.of(
            "email", user.getEmail(),
            "username", user.getUsername()
    ));
  }

  @Operation(summary = "회원가입 - 이메일 중복확인, 이메일 인증")
  @GetMapping("/check-email")
  public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam String email) {
    boolean isDuplicate = userService.checkEmailDuplicate(email);
    return ResponseEntity.ok(isDuplicate);
  }

  @Operation(summary = "회원가입 - 닉네임 중복확인")
  @GetMapping("/check-nickname")
  public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
    boolean isDuplicate = userService.checkNicknameDuplicate(nickname);
    return ResponseEntity.ok(isDuplicate);
  }

  @Operation(summary = "로그인")
  @PostMapping("/login")
  public ApiResponseJson login(@RequestBody @Valid UserLoginDto userLoginDto) {
    TokenInfo tokenInfo = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
    log.info("Token issued: {}", tokenInfo);
    return new ApiResponseJson(HttpStatus.OK, tokenInfo);
  }

  // + 소셜 로그인, 비밀번호 찾기
}
