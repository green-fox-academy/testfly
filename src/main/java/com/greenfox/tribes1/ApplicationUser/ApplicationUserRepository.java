package com.greenfox.tribes1.ApplicationUser;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

  Optional<ApplicationUser> findByUsername(String username);

  boolean existsByUsername(String username);
}
