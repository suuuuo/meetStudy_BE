package com.elice.meetstudy.domain.user.controller;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody UserJoinDto userJoinDto) {
        try {
            User user = userService.join(userJoinDto);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
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

    // 로그인 - 이메일 확인

    // 로그인 - 비밀번호 확인



    // + 소셜 로그인, 비밀번호 찾기
}
