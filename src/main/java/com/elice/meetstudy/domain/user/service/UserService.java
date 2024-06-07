package com.elice.meetstudy.domain.user.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.user.domain.Interest;
import com.elice.meetstudy.domain.user.domain.Role;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.dto.UserJoinDto;
import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenInfo;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private static final int MAX_LENGTH = 10;

  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenProvider tokenProvider;

  // 회원가입
  public User join(UserJoinDto userJoinDto) {
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
            .createdAt(LocalDateTime.now())
            .role(Role.USER)
            .build();

    // 먼저 유저를 저장하여 ID를 생성
    user = userRepository.save(user);

    List<Long> interestIds = userJoinDto.getInterests();
    for (Long categoryId : interestIds) {
      Category category = categoryRepository.findById(categoryId)
              .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
      Interest interest = Interest.createInterest(category);
      user.addInterest(interest);
    }

    // 관심사를 포함한 유저를 다시 저장
    return userRepository.save(user);
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
      } catch (IllegalArgumentException | BadCredentialsException e) {
        throw new IllegalArgumentException("계정이 존재하지 않거나 비밀번호가 잘못되었습니다");
      }
    } else {
      throw new IllegalArgumentException("계정이 존재하지 않거나 비밀번호가 잘못되었습니다");
    }
  }

  // 로그인 - 이메일 확인
  public User findUserByEmail(String email){

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

  // + 소셜 로그인
}