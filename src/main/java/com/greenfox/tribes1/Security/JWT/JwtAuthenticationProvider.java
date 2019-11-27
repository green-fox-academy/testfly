package com.greenfox.tribes1.Security.JWT;

import com.greenfox.tribes1.Security.Config.JwtSettings;
import com.greenfox.tribes1.Security.Model.RawAccessJwtToken;
import com.greenfox.tribes1.Security.Model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
  
  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    RawAccessJwtToken rawAccessJwtToken = (RawAccessJwtToken) authentication.getCredentials();
    
    Jws<Claims> jwsClaims = rawAccessJwtToken.parseClaims(JwtSettings.TOKEN_SIGNING_KEY);
    String subject = jwsClaims.getBody().getSubject();
    List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
    List<GrantedAuthority> authorities = scopes.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());
    
    UserContext context = UserContext.create(subject, authorities);
    
    return new JwtAuthenticationToken(context, context.getAuthorities());
  }
  
  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
