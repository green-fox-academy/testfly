package com.greenfox.tribes1.Security.Model;


import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class UserContext {
  private final String username;
  private final List<GrantedAuthority> authorities;

  private UserContext(String username, List<GrantedAuthority> authorities) {
    this.username = username;
    this.authorities = authorities;
  }

  public static UserContext create(String username, List<GrantedAuthority> authorities) {
    return new UserContext(username, authorities);
  }

}
