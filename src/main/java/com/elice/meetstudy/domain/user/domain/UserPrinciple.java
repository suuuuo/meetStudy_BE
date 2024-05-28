package com.elice.meetstudy.domain.user.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


import java.util.Collection;

@Getter
public class UserPrinciple extends User {

    private static final String PASSWORD_ERASED_VALUE = "[PASSWORD_ERASED]";
    private final String loginId;

    public UserPrinciple(String loginId, String username,
                         Collection<? extends GrantedAuthority> authorities) {
        super(username, PASSWORD_ERASED_VALUE, authorities);
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        return "UserPrinciple(" +
                "loginId=" + loginId +
                " username=" + getUsername() +
                " role=" + getAuthorities() +
                ")";
    }
}