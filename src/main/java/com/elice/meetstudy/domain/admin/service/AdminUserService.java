package com.elice.meetstudy.domain.admin.service;

import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    // 모든 회원 조회
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findByRole(List.of("USER"), pageable);
    }

    // id로 회원 조회
    public User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    // 회원 삭제
    public void deleteUser(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(foundUser);
    }
}
