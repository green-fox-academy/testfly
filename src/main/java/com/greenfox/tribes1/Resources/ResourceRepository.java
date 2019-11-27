package com.greenfox.tribes1.Resources;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

  @Override
  Optional<Resource> findById(Long id);
}