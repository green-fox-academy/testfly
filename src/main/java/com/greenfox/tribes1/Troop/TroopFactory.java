package com.greenfox.tribes1.Troop;

import com.greenfox.tribes1.Exception.NotValidResourceException;
import com.greenfox.tribes1.Resources.ResourceService;
import com.greenfox.tribes1.Troop.Model.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TroopFactory {

  private ResourceService resourceService;

  @Autowired
  public TroopFactory(ResourceService resourceService) {
    this.resourceService = resourceService;
  }

  public TroopFactory() {

  }

  public static Troop createTroop(TroopType troopType) {
    return troopType.makeTroop();
  }

  public Troop makeTroop(TroopType troopType) throws NotValidResourceException {

    /*List<Food> food =


     probably have to move it to ProgressionService,
     but consult with Sol first

   TODO:copy
    Food food =
        Iterables.getOnlyElement( //casts List<Food> to Food as well
            troopType.makeTroop()
                .getKingdom()
                .getResources()
                .stream()
                .filter(resource -> resource instanceof Food)
                .map(f -> (Food) f)
                .collect(Collectors.toList()));

//troopUgprade(){

decreaseResource();
}


    food.setAmountPerMinute(food.getAmountPerMinute() - 1);
    resourceService.saveResource(food);

    //Preconditions.checkArgument(food.size() == 1); Not needed due to getOnlyElement
  */

    return troopType.makeTroop();
  }
}
