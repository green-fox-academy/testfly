package com.greenfox.tribes1.Building;

import com.greenfox.tribes1.Exception.BuildingIdNotFoundException;
import com.greenfox.tribes1.Exception.BuildingNotValidException;
import com.greenfox.tribes1.KingdomElementService;
import com.greenfox.tribes1.Upgradable;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildingService implements KingdomElementService<Building>, Upgradable<Building>{

  private BuildingRepository buildingRepository;
  //private Predicate<Building> isValid = (a) -> (a != null);

  @Autowired
  public BuildingService(BuildingRepository buildingRepository) {
    this.buildingRepository = buildingRepository;
  }

  /*public boolean isValidBuilding(Building building) {
    return building != null;
  }*/
  /*
  public void upgradeBarracks(Building buildingToUpgrade) throws BuildingNotValidException {
    buildingToUpgrade.setLevel(buildingToUpgrade.getLevel() + 1L);
    buildingToUpgrade.setHP(buildingToUpgrade.getHP() * 1.1F);
    save(buildingToUpgrade);
  }

  public void upgradeFarm(Building buildingToUpgrade) throws BuildingNotValidException {
    buildingToUpgrade.setLevel(buildingToUpgrade.getLevel() + 1L);
    buildingToUpgrade.setHP(buildingToUpgrade.getHP() * 1.1F);
    save(buildingToUpgrade);
  }

  public void upgradeMine(Building buildingToUpgrade) throws BuildingNotValidException {
    buildingToUpgrade.setLevel(buildingToUpgrade.getLevel() + 1L);
    buildingToUpgrade.setHP(buildingToUpgrade.getHP() * 1.1F);
    save(buildingToUpgrade);
  }*/





  @Override
  public Building findById(Long id) throws BuildingIdNotFoundException {
    return buildingRepository.findById(id)
            .orElseThrow(() -> new BuildingIdNotFoundException(("There is no Building with such Id")));
  }

  @Override
  @SneakyThrows
  public Building save(Optional<Building> building) {
    return buildingRepository.save(building
            .orElseThrow(() -> new BuildingNotValidException("Building is not valid")));
  }

  @Override
  public void refresh(Building building) {
    //TODO: IF any progression is over, create new Building();
  }

  @Override
  public void upgrade(Building building) {
    building.buildingUpgrade();
    save(Optional.of(building));
  }
}