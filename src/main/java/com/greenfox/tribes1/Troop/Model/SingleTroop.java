package com.greenfox.tribes1.Troop.Model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "SingleTroop")
@DiscriminatorValue("SingleTroop")
@Getter
@Setter
public class SingleTroop extends Troop {

  public SingleTroop() {
    this.setLevel(1L);
    this.setHP(20F);
    this.setAttack(1L);
    this.setDefense(1L);
  }

}
