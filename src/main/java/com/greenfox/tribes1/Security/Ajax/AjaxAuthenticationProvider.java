package com.greenfox.tribes1.Security.Ajax;

import com.greenfox.tribes1.ApplicationUser.ApplicationUser;
import com.greenfox.tribes1.ApplicationUser.ApplicationUserService;
import com.greenfox.tribes1.Security.Model.UserContext;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

  private final BCryptPasswordEncoder encoder;
  private final ApplicationUserService userService;

  @Autowired
  public AjaxAuthenticationProvider(final BCryptPasswordEncoder encoder, final ApplicationUserService userService) {
    this.encoder = encoder;
    this.userService = userService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    Assert.notNull(authentication, "No authentication data provided");
    String username = (String) authentication.getPrincipal();
    String password = (String) authentication.getCredentials();
    ApplicationUser applicationUser = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No such user: " + username));

    if (!encoder.matches(password, applicationUser.getPassword())) {
      throw new BadCredentialsException("Wrong password!");
    }

    if (applicationUser.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");

    List<GrantedAuthority> authorities = applicationUser.getRoles().stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getRoleType().authority()))
            .collect(Collectors.toList());

    UserContext userContext = UserContext.create(applicationUser.getUsername(), authorities);
    return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
