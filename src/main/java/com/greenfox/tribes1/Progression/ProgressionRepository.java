package com.greenfox.tribes1.Progression;

import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface ProgressionRepository extends JpaRepository<Progression, Long> {
  List<Progression> findByTypeAndFinishedIsLessThanAndLevelEquals(String type, Timestamp now, Long level);
  List<Progression> findByTypeAndFinishedIsLessThanAndLevelGreaterThan(String type, Timestamp now, Long level);
  List<Progression> findByFinishedLessThan(Timestamp now);
}
