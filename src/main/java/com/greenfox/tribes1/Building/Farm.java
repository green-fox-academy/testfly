package com.greenfox.tribes1.Building;

import com.google.common.collect.Iterables;
import com.greenfox.tribes1.Resources.Food;
import com.greenfox.tribes1.Resources.Resource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Farm")
@DiscriminatorValue("Farm")
@Getter
@Setter
public class Farm extends Building {

  public Farm() {
    this.setLevel(1L);
    this.setHP(150.0f);
  }


  @Override
  public void buildingUpgrade() {
    levelUp();

    List<Resource> resourceList = getKingdom().getResources();

    List<Resource> filteredResourceListForFood = resourceList.stream().filter(r -> r instanceof Food).collect(Collectors.toList());
    Food food = (Food) Iterables.getOnlyElement(filteredResourceListForFood);

    food.setAmountPerMinute(food.getAmountPerMinute() + 5L);

  }


}
