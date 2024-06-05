package com.elice.meetstudy.domain.user.service;

import com.elice.meetstudy.domain.category.entity.Category;
import com.elice.meetstudy.domain.category.repository.CategoryRepository;
import com.elice.meetstudy.domain.post.domain.Post;
import com.elice.meetstudy.domain.scrap.domain.Scrap;
import com.elice.meetstudy.domain.scrap.repository.ScrapRepository;
import com.elice.meetstudy.domain.studyroom.repository.UserStudyRoomRepository;
import com.elice.meetstudy.domain.user.domain.Interest;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.domain.UserPrinciple;
import com.elice.meetstudy.domain.user.dto.UserUpdateDto;
import com.elice.meetstudy.domain.user.jwt.token.TokenProvider;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MyPageService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ScrapRepository scrapRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public long getUserId(){
        //접근한 유저 정보 가져오는 로직
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple userPrinciple = (UserPrinciple)authentication.getPrincipal();
        return Long.parseLong(userPrinciple.getUserId());
    }

    // 회원 정보 조회
    @Transactional
    public User getUserByUserId(){

//        Long userId = getUserId();
        Long userId = 18L;

        return userRepository.findUserByUserId(userId);
    }

    @Transactional
    // 회원 정보 수정
    public User updateUser(UserUpdateDto userUpdateDto) {
//        Long userId = getUserId();
        Long userId = 18L;

        User updateUser = userRepository.findUserByUserId(userId);

        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            updateUser.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

        if (userUpdateDto.getUsername() != null && !userUpdateDto.getUsername().isEmpty()) {
            updateUser.setUsername(userUpdateDto.getUsername());
        }

        if (userUpdateDto.getNickname() != null && !userUpdateDto.getNickname().isEmpty()) {
            updateUser.setNickname(userUpdateDto.getNickname());
        }

        if (userUpdateDto.getInterests() != null && !userUpdateDto.getInterests().isEmpty()) {
            // 기존 관심사 삭제
            updateUser.clearInterests();

            // 새로운 관심사 추가
            List<Long> interestIds = userUpdateDto.getInterests();
            for (Long categoryId : interestIds) {
                Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
                Interest interest = Interest.createInterest(updateUser, category); // 관심사를 생성하고 사용자에게 추가
                updateUser.addInterest(interest);
            }
        }

        return userRepository.save(updateUser); // 관심사가 추가된 사용자를 한 번에 저장
    }


    // 회원 삭제 (탈퇴)
    @Transactional
    public void delete() {

//        Long userId = getUserId();
        Long userId = 12L;

        User deleteUser = userRepository.findUserByUserId(userId);

        // 탈퇴 시간
        deleteUser.updateDeletedAt();
    }

//    // 참여한 스터디룸 조회
//    @Transactional
//    public List<UserStudyRoom> getStudyRoomsByUserId(String token) {
//        Long userId = getUserIdFromToken(token);
//        return userStudyRoomRepository.findStudyRoomsByUserId(userId);
//    }

    // 스크랩 한 게시글 조회
    @Transactional
    public List<Post> getScrappedPostsByUserId() {
        Long userId = getUserId();
        List<Scrap> scraps = scrapRepository.findScrapsByUserId(userId);
        return scraps.stream()
            .map(Scrap::getPost)
            .collect(Collectors.toList());
    }

    // + 프로필 설정 (보유 자격증 등록)
}
