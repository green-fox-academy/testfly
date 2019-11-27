package com.greenfox.tribes1.Security.JWT.Extractor;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

  @Override
  public String extract(String header) {
    System.out.println(header);
    if (header == null) {
      throw new AuthenticationServiceException("Authorization header cannot be blank!");
    }
    String HEADER_PREFIX = "Bearer ";
    return header.substring(HEADER_PREFIX.length(), header.length());
  }
}
