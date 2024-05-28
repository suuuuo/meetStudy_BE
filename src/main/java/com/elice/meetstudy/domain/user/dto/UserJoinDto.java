package com.elice.meetstudy.domain.user.dto;

import com.elice.meetstudy.domain.user.domain.Interest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserJoinDto {
    private String email;
    private String password;
    private String username;
    private String nickname;
    private Interest interest;
}
