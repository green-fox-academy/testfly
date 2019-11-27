package com.greenfox.tribes1.Kingdom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KingdomRepository extends JpaRepository<Kingdom, Long> {

  Kingdom findKingdomByApplicationUser_Username(String applicationUserName);
}
