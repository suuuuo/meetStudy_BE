package com.elice.meetstudy.domain.user.controller;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.EmailCheckDto;
import com.elice.meetstudy.domain.user.dto.EmailRequestDto;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.dto.UserLoginDto;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenInfo;
import com.elice.meetstudy.domain.user.jwt.web.json.ApiResponseJson;
import com.elice.meetstudy.domain.user.service.MailService;
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
  private final MailService mailService;

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

  @Operation(summary = "회원가입 - 이메일 인증 메일 발송", description = "사용자가 적은 이메일로 인증코드를 발송합니다.")
  @PostMapping ("/mailSend")
  public String mailSend(@RequestBody @Valid EmailRequestDto emailDto){
    System.out.println("이메일 인증 이메일 :"+emailDto.getEmail());
    return mailService.joinEmail(emailDto.getEmail());
  }

  @Operation(summary = "회원가입 - 이메일 인증 메일 확인", description = "이메일에 보낸 인증코드가 맞는지 확인합니다.")
  @PostMapping("/mailauthCheck")
  public String AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto){
    Boolean Checked=mailService.CheckAuthNum(emailCheckDto.getEmail(),emailCheckDto.getAuthNum());
    if(Checked){
      return "ok";
    }
    else{
      throw new NullPointerException("잘못된 인증코드입니다.");
    }
  }

  @Operation(summary = "회원가입 - 이메일 중복확인", description = "이메일을 중복확인합니다.")
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

  @Operation(summary = "비밀번호 찾기", description = "이메일로 임시 비밀번호를 발송합니다.")
  @PostMapping("/findPassword")
  public String findPassword(@RequestBody @Valid EmailRequestDto emailDto) {
    return mailService.passwordEmail(emailDto.getEmail());
  }

}
