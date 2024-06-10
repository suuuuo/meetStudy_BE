package com.elice.meetstudy.domain.user.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class MyPageDto {
    private String email;
    private String password;
    private String username;
    private String nickname;
    private List<Long> interests;
}
