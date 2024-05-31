package com.elice.meetstudy.domain.user.service;

import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.user.domain.Role;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenInfo;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@S!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
    private static final int MAX_LENGTH = 10;

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public User join(UserJoinDto userJoinDto) {
        passwordCheck(userJoinDto.getPassword());
        usernameCheck(userJoinDto.getUsername());
        nicknameCheck(userJoinDto.getNickname());
        checkInterestsCount(userJoinDto.getInterests());

        if (checkEmailDuplicate(userJoinDto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (checkNicknameDuplicate(userJoinDto.getNickname())) {
            throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
        }

        User user = User.builder()
                .email(userJoinDto.getEmail())
                .password(passwordEncoder.encode(userJoinDto.getPassword()))
                .username(userJoinDto.getUsername())
                .nickname(userJoinDto.getNickname())
                .role(Role.USER)
                .build();

//        userJoinDto.getInterests().forEach(categoryId -> {
//            Category category = categoryRepository.findById(categoryId)
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
//            Interest interest = new Interest(user, category);
//            user.addInterest(interest);
//        });

        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }


    // 비밀번호 조건 (8자 이상/ 영문+숫자+특수문자 조합)
    public void passwordCheck(String password) {
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호는 최소 8자리에 영어, 숫자, 특수문자를 포함해야 합니다.");
        }
    }

    // 이름 조건 (10자 이하)
    public void usernameCheck(String username){
        if (username.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("이름은 최대 10자까지 허용됩니다.");
        }
    }

    // 닉네임 조건 (10자 이하)
    public void nicknameCheck(String nickname){
        if (nickname.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임은 최대 10자까지 허용됩니다.");
        }
    }

    // 이메일 중복확인, 이메일 인증
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    // 닉네임 중복확인
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    // 관심분야 최대 3개 선택
    private void checkInterestsCount(List<Long> interests) {
        if (interests.size() > 3) {
            throw new IllegalArgumentException("관심분야는 최대 3개까지 허용됩니다.");
        }
    }


    // 로그인
    public TokenInfo login(String email, String password) {
        User user = findUserByEmail(email);

        if (user.getDeletedAt() == null) {
            try {
                checkPassword(password, user);
                return tokenProvider.createToken(user);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("계정이 존재하지 않거나 비밀번호가 잘못되었습니다");
            }
        } else {
            throw new IllegalArgumentException("계정이 존재하지 않거나 비밀번호가 잘못되었습니다");
        }
    }

    // 로그인 - 이메일 확인
    private User findUserByEmail(String email){

        return userRepository.findByEmail(email).orElseThrow(() -> {
            return new IllegalArgumentException("계정이 존재하지 않습니다.");
        });
    }

    // 로그인 - 비밀번호 확인
    private void checkPassword(String loginPwd, User user){
        if (!passwordEncoder.matches(loginPwd, user.getPassword())) {
            throw new BadCredentialsException("기존 비밀번호 확인에 실패하셨습니다");
        }
    }



    // + 소셜 로그인, 비밀번호 찾기

}
