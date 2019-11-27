package com.greenfox.tribes1.Resources;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Food")
@DiscriminatorValue("Food")
@Getter
@Setter
public class Food extends Resource {

  public Food() {
    this.setAmountPerMinute(8L);
  }
}


