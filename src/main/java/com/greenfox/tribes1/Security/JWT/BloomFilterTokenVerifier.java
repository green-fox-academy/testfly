package com.greenfox.tribes1.Security.JWT;

import org.springframework.stereotype.Component;

@Component
public class BloomFilterTokenVerifier implements TokenVerifier {
  @Override
  public boolean verify(String jti) {
    return true;
  }
}