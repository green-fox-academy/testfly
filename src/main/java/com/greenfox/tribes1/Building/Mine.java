package com.greenfox.tribes1.Building;

import com.google.common.collect.Iterables;
import com.greenfox.tribes1.Resources.Gold;
import com.greenfox.tribes1.Resources.Resource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Mine")
@DiscriminatorValue("Mine")
@Getter
@Setter
public class Mine extends Building {

  public Mine() {
    this.setLevel(1L);
    this.setHP(200.0f);
  }

  @Override
  void buildingUpgrade() {
    levelUp();

    List<Resource> resourceList = getKingdom().getResources();

    List<Resource> filteredResourceListForGold = resourceList.stream().filter(r -> r instanceof Gold).collect(Collectors.toList());
    Gold gold = (Gold) Iterables.getOnlyElement(filteredResourceListForGold);

    gold.setAmountPerMinute(gold.getAmountPerMinute() + 5L);
  }
}
