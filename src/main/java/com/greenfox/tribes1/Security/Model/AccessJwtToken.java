package com.greenfox.tribes1.Security.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.Claims;
import lombok.Getter;

@Getter
public class AccessJwtToken implements JwtToken {
  private final String rawToken;
  @JsonIgnore
  private Claims claims;

  protected AccessJwtToken(final String token, Claims claims) {
    this.rawToken = token;
    this.claims = claims;
  }

  @Override
  public String getToken() {
    return this.rawToken;
  }
}
