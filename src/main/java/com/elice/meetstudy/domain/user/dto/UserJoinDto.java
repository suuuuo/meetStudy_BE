package com.elice.meetstudy.domain.user.dto;

import com.elice.meetstudy.domain.user.domain.Interest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor // AccessLevel을 Protected로 설정해야할까요?
public class UserJoinDto {
    // @NotBlank = null 과 "" 과 " " 모두 비허용, @Notnull = "" 이나 " " 은 허용, @NotEmpty = null 과 "" 은 불가, " " 은 허용

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@S!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$", message = "비밀번호는 최소 8자리에 영어, 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Size(min = 2, max = 10, message = "이름은 2자 이상 10자 이하이어야 합니다.")
    private String username;

    @Size(min = 1, max = 10, message = "닉네임은 1자 이상 10자 이하이어야 합니다.")
    private String nickname;

    private List<Long> interests;
}
