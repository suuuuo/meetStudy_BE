package com.elice.meetstudy.domain.user.dto;

import com.elice.meetstudy.domain.user.domain.Interest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // AccessLevel을 Protected로 설정해야할까요?
public class UserJoinDto {
    private String email;
    private String password;
    private String username;
    private String nickname;
    private List<Long> interests;
}
