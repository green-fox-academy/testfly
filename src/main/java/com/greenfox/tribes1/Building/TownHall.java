package com.greenfox.tribes1.Building;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "TownHall")
@DiscriminatorValue("TownHall")
@Getter
@Setter
public class TownHall extends Building {

  public TownHall() {
    this.setLevel(1L);
    this.setHP(5000.0f);
  }

  @Override
  void buildingUpgrade() {
    levelUp();
  }
}
