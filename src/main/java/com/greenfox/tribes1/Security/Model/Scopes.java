package com.greenfox.tribes1.Security.Model;

public enum Scopes {
  REFRESH_TOKEN;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
