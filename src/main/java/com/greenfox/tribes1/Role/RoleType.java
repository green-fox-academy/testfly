package com.greenfox.tribes1.Role;

public enum RoleType {
  ADMIN, PREMIUM_MEMBER, MEMBER;

  public String authority() {
    return "ROLE_" + this.name();
  }
}
