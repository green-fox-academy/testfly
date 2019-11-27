package com.greenfox.tribes1.Troop;

import com.greenfox.tribes1.Troop.Model.Troop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TroopRepository extends JpaRepository<Troop, Long> {
}
