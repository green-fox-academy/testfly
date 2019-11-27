package com.greenfox.tribes1;

import com.greenfox.tribes1.ApplicationUser.ApplicationUser;
import com.greenfox.tribes1.ApplicationUser.ApplicationUserRepository;
import com.greenfox.tribes1.ApplicationUser.ApplicationUserService;
import com.greenfox.tribes1.Security.Model.AccessJwtToken;
import com.greenfox.tribes1.Security.Model.JwtToken;
import com.greenfox.tribes1.Security.Model.JwtTokenFactory;
import com.greenfox.tribes1.Security.Model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestTokenProvider {

  private JwtTokenFactory jwtTokenFactory;

  @Autowired
  public TestTokenProvider(JwtTokenFactory jwtTokenFactory) {
    this.jwtTokenFactory = jwtTokenFactory;
  }

  public String createMockToken(String username) {
    UserContext userContext = UserContext.create(username, Collections.emptyList());
    AccessJwtToken token = jwtTokenFactory.createAccessJwtToken(userContext);
    return "Bearer " + token.getToken();
  }
}