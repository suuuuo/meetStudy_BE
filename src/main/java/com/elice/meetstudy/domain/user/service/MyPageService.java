package com.elice.meetstudy.domain.user.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.user.domain.Interest;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.dto.UserUpdateDto;
import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.jwt.token.TokenType;
import com.elice.meetstudy.domain.user.jwt.token.dto.TokenValidationResult;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    // 사용자 ID 추출 메서드
    @Transactional
    public Long getUserIdFromToken(String token) {
        if (token != null) {
            TokenValidationResult tokenValidationResult = tokenProvider.validateToken(token);
            Claims claims = tokenValidationResult.getClaims();

            if (tokenValidationResult.getTokenType() == TokenType.ACCESS) { // 타입이 access일 때 -> 제대로 로그인됨 or 토큰 만료
                if (claims != null) { // 토큰 만료되지 않음
                    return Long.parseLong(claims.getSubject());
                }
            }
        }
        throw new UsernameNotFoundException("Unable to extract user ID from token");
    }

    // 회원 정보 조회
    @Transactional
    public User getUserByUserId(String token){

        Long userId = getUserIdFromToken(token);

        return userRepository.findUserByUserId(userId);
    }


//    // 회원 정보 수정
//    public User updateUser(String token, UserUpdateDto userUpdateDto) {
//        Long userId = getUserIdFromToken(token);
//
//        User updateUser = userRepository.findUserByUserId(userId);
//
//        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
//            userService.passwordCheck(userUpdateDto.getPassword());
//            updateUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
//        }
//
//        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isEmpty()) {
//            userService.usernameCheck(userUpdateDto.getUsername());
//            updateUser.setUsername(userUpdateDto.getUsername());
//        }
//
//        if (userUpdateDto.getNickname() != null && !userUpdateDto.getNickname().isEmpty()) {
//            userService.nicknameCheck(userUpdateDto.getNickname());
//            updateUser.setNickname(userUpdateDto.getNickname());
//        }
//
//        if (userUpdateDto.getInterests() != null && !userUpdateDto.getInterests().isEmpty()) {
//            // Clear existing interests
//            updateUser.clearInterests();
//
//            // Add new interests
//            userUpdateDto.getInterests().forEach(categoryId -> {
//                Category category = categoryRepository.findById(categoryId)
//                        .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
//                Interest interest = new Interest(updateUser, category);
//                updateUser.addInterest(interest);
//            });
//        }
//
//        return userRepository.save(updateUser);
//    }

    // 회원 삭제 (탈퇴)
    @Transactional
    public void delete(String token) {

        Long userId = getUserIdFromToken(token);

        User deleteUser = userRepository.findUserByUserId(userId);

        // 탈퇴 시간
        deleteUser.updateDeletedAt();
    }

    // 참여한 스터디룸 조회

    // 스크랩 한 게시글 조회

    // + 프로필 설정 (보유 자격증 등록), 내가 작성한 게시글, 스터디 성취율
}
