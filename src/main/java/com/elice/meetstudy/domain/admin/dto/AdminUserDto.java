package com.elice.meetstudy.domain.admin.dto;

import com.elice.meetstudy.domain.user.domain.Role;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUserDto {
    private Long id;
    private String email;
    private String password;
    private String username;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Role role;
    private List<String> interests;
}
