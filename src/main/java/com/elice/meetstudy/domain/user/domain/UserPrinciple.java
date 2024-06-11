package com.elice.meetstudy.domain.user.domain;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class UserPrinciple extends User {

  private static final String PASSWORD_ERASED_VALUE = "[PASSWORD_ERASED]";
  private final String userId;

  public UserPrinciple(
      String userId, String username, Collection<? extends GrantedAuthority> authorities) {
    super(username, PASSWORD_ERASED_VALUE, authorities);
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "UserPrinciple("
        + "userId="
        + userId
        + " username="
        + getUsername()
        + " role="
        + getAuthorities()
        + ")";
  }
}
