package com.elice.meetstudy.domain.admin.service;

import com.elice.meetstudy.domain.admin.dto.AdminUserDto;
import com.elice.meetstudy.domain.user.domain.User;
import com.elice.meetstudy.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserService {

    private final UserRepository userRepository;

    // User를 UserDto로 변환하는 메서드
    private AdminUserDto convertToDto(User user) {
        List<String> interestNames = user.getInterests().stream()
                .map(interest -> interest.getCategory().getName())
                .collect(Collectors.toList());

        return new AdminUserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getUsername(),
                user.getNickname(),
                user.getCreatedAt(),
                user.getDeletedAt(),
                user.getRole(),
                interestNames
        );
    }

    // 모든 회원 조회
    public List<AdminUserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // id로 회원 조회
    public AdminUserDto findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 회원을 찾을 수 없습니다. [ID: " + id + "]"));

        return convertToDto(user);
    }

    // 회원 삭제
    public void deleteUser(Long id) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        userRepository.delete(foundUser);
    }
}
