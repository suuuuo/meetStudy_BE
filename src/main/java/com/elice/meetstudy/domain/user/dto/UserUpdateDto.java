package com.elice.meetstudy.domain.user.dto;

import com.elice.meetstudy.domain.user.domain.Interest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateDto {


    private String password;
    private String username;
    private String nickname;
    private List<Long> interests;
}
