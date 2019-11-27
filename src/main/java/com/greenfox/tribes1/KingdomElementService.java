package com.greenfox.tribes1;

import java.util.Optional;

public interface KingdomElementService<T> {

  //TODO: throws Expression is not OK here

  T findById(Long id) throws Exception;

  T save(Optional<T> object) throws Exception;

  void refresh(T t) throws Exception;
}