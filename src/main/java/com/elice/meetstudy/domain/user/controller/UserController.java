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
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "A. 유저", description = "유저 접속 관련 API 입니다.")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입", description = "이메일, 비밀번호, 이름, 닉네임, 관심분야를 입력받아 회원가입합니다.")
  @PostMapping("/join")
  public ApiResponseJson join(@RequestBody @Valid UserJoinDto userJoinDto) {

    User user = userService.join(userJoinDto);
    return new ApiResponseJson(
        HttpStatus.OK,
        Map.of(
            "email", user.getEmail(),
            "username", user.getUsername()));
  }

  @Operation(summary = "회원가입 - 이메일 중복확인", description = "이메일을 중복확인합니다. (이메일 인증은 구현보류)")
  @GetMapping("/check-email")
  public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam String email) {
    boolean isDuplicate = userService.checkEmailDuplicate(email);
    return ResponseEntity.ok(isDuplicate);
  }

  @Operation(summary = "회원가입 - 닉네임 중복확인", description = "닉네임을 중복확인합니다.")
  @GetMapping("/check-nickname")
  public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
    boolean isDuplicate = userService.checkNicknameDuplicate(nickname);
    return ResponseEntity.ok(isDuplicate);
  }

  @Operation(summary = "로그인", description = "이메일, 비밀번호를 입력하여 로그인합니다.")
  @PostMapping("/login")
  public ApiResponseJson login(@RequestBody @Valid UserLoginDto userLoginDto) {
    TokenInfo tokenInfo = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
    log.info("Token issued: {}", tokenInfo);
    return new ApiResponseJson(HttpStatus.OK, tokenInfo);
  }

  // + 소셜 로그인, 비밀번호 찾기
}
