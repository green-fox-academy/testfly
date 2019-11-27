package com.greenfox.tribes1.Security;

import com.greenfox.tribes1.ApplicationUser.ApplicationUser;

import java.util.Optional;

public interface UserService {
  Optional<ApplicationUser> getByUsername(String username);
}