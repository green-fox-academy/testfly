package com.greenfox.tribes1.Building;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Barracks")
@DiscriminatorValue("Barracks")
@Getter
@Setter
public class Barracks extends Building {

  public Barracks() {
    this.setLevel(1L);
    this.setHP(250.0f);
  }


  @Override
  void buildingUpgrade() {
    levelUp();
  }
}
