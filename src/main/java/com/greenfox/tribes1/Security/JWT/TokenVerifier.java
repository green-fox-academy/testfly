package com.greenfox.tribes1.Security.JWT;

public interface TokenVerifier {
  boolean verify(String jti);
}
