package com.elice.meetstudy.domain.user.controller;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.dto.UserLoginDto;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenInfo;
import com.elice.meetstudy.domain.user.jwt.web.json.ApiResponseJson;
import com.elice.meetstudy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/vi/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

  private final UserService userService;

  // 회원가입
  @PostMapping("/join")
  public ApiResponseJson join(@RequestBody UserJoinDto userJoinDto){

    User user = userService.join(userJoinDto);
    return new ApiResponseJson(HttpStatus.OK, Map.of(
            "email", user.getEmail(),
            "username", user.getUsername()
    ));
  }

  // 회원가입 - 이메일 중복확인, 이메일 인증
  @GetMapping("/check-email")
  public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam String email) {
    boolean isDuplicate = userService.checkEmailDuplicate(email);
    return ResponseEntity.ok(isDuplicate);
  }

  // 회원가입 - 닉네임 중복확인
  @GetMapping("/check-nickname")
  public ResponseEntity<Boolean> checkNicknameDuplicate(@RequestParam String nickname) {
    boolean isDuplicate = userService.checkNicknameDuplicate(nickname);
    return ResponseEntity.ok(isDuplicate);
  }

  // 관심분야 선택 (최대 3개)

  // 로그인
  @PostMapping("/login")
  public ApiResponseJson login(@RequestBody UserLoginDto userLoginDto) {
    TokenInfo tokenInfo = userService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
    log.info("Token issued: {}", tokenInfo);
    return new ApiResponseJson(HttpStatus.OK, tokenInfo);
  }

  // + 소셜 로그인, 비밀번호 찾기
}
