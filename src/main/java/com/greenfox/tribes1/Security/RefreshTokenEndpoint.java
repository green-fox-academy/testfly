package com.greenfox.tribes1.Security;

import com.greenfox.tribes1.ApplicationUser.ApplicationUser;
import com.greenfox.tribes1.Security.Config.JwtSettings;
import com.greenfox.tribes1.Security.Config.WebSecurityConfig;
import com.greenfox.tribes1.Security.JWT.Extractor.TokenExtractor;
import com.greenfox.tribes1.Security.JWT.TokenVerifier;
import com.greenfox.tribes1.Security.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@RestController
public class RefreshTokenEndpoint {
  
  private JwtTokenFactory tokenFactory;
  private UserService userService;
  private TokenVerifier tokenVerifier;
  @Qualifier("jwtHeaderTokenExtractor")
  private TokenExtractor tokenExtractor;
  
  @Autowired
  public RefreshTokenEndpoint(JwtTokenFactory tokenFactory, UserService userService, TokenVerifier tokenVerifier, TokenExtractor tokenExtractor) {
    this.tokenFactory = tokenFactory;
    this.userService = userService;
    this.tokenVerifier = tokenVerifier;
    this.tokenExtractor = tokenExtractor;
  }
  
  @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
  public @ResponseBody
  JwtToken refreshToken(HttpServletRequest request) {
    String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));
    
    RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
    RefreshToken refreshToken = RefreshToken.create(rawToken, JwtSettings.TOKEN_SIGNING_KEY).orElseThrow(UnsupportedOperationException::new);
    
    String jti = refreshToken.getJti();
    if (!tokenVerifier.verify(jti)) {
      throw new UnsupportedOperationException();
    }
    
    String subject = refreshToken.getSubject();
    ApplicationUser user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("No such user: " + subject));
    
    UserContext userContext = UserContext.create(user.getUsername(), Collections.emptyList());
    
    return tokenFactory.createAccessJwtToken(userContext);
  }
}