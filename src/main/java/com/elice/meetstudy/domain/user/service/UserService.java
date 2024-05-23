package com.elice.meetstudy.domain.user.service;

import com.elice.meetstudy.domain.user.domain.Role;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class UserService {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@S!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final int MAX_LENGTH = 10;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입
    public User join(UserJoinDto userJoinDto){

        passwordCheck(userJoinDto.getPassword());
        usernameCheck(userJoinDto.getUsername());
        nicknameCheck(userJoinDto.getNickname());

        User user = User.builder()
                .email(userJoinDto.getEmail())
                .password(passwordEncoder.encode(userJoinDto.getPassword()))
                .username(userJoinDto.getUsername())
                .nickname(userJoinDto.getNickname())
                .role(Role.USER)
                .build();

        user.setJoinAt(LocalDateTime.now());


        return userRepository.save(user);
    }

    // 회원가입 - 비밀번호 조건 (8자 이상/ 영문+숫자+특수문자 조합)
    private void passwordCheck(String password){
        if (PASSWORD_PATTERN.matcher(password).matches()) {
            return;
        }

        //log.info("비밀번호 조건 미달");
        throw new IllegalArgumentException("비밀번호는 최소 8자리에 영어, 숫자, 특수문자를 포함해야 합니다.");

    }

    // 회원가입 - 이름 조건 (10자 이하)
    private void usernameCheck(String username){
        if (username.length() <= MAX_LENGTH) {
            return;
        }
        throw new IllegalArgumentException("이름은 최대 10자까지 허용됩니다.");
    }

    // 회원가입 - 닉네임 조건 (10자 이하)
    private void nicknameCheck(String nickname){
        if (nickname.length() <= MAX_LENGTH) {
            return;
        }
        throw new IllegalArgumentException("닉네임은 최대 10자까지 허용됩니다.");
    }

    // 회원가입 - 이메일 중복확인, 이메일 인증
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 회원가입 - 닉네임 중복확인
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 관심분야 선택 (최대 3개)

    // 로그인

    // 로그인 - 이메일 확인

    // 로그인 - 비밀번호 확인



    // + 소셜 로그인, 비밀번호 찾기





}
