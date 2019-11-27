package com.greenfox.tribes1.Purchase;

import com.google.common.collect.Iterables;
import com.greenfox.tribes1.Building.Barracks;
import com.greenfox.tribes1.Building.Building;
import com.greenfox.tribes1.Building.BuildingService;
import com.greenfox.tribes1.Building.TownHall;
import com.greenfox.tribes1.Exception.*;
import com.greenfox.tribes1.Kingdom.Kingdom;
import com.greenfox.tribes1.Resources.Gold;
import com.greenfox.tribes1.Resources.Resource;
import com.greenfox.tribes1.Resources.ResourceService;
import com.greenfox.tribes1.Troop.Model.Troop;
import com.greenfox.tribes1.Troop.TroopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseService {

  private BuildingService buildingService;
  private ResourceService resourceService;
  private Long troopUpgradeCost = 10L;
  private Long buildingUpgradeCost = 100L;

  @Autowired
  public PurchaseService(BuildingService buildingService, ResourceService resourceService) {
    this.buildingService = buildingService;
    this.resourceService = resourceService;
  }

  public Resource purchaseBuilding(Kingdom kingdom) throws GoldNotEnoughException, NotValidResourceException {
    List<Resource> kingdomResources = kingdom.getResources();
    Gold gold = getGold(kingdomResources);
    return purchaseIfPossible(gold, 1L, buildingUpgradeCost);
  }

  public Resource purchaseTroop(Kingdom kingdom) throws GoldNotEnoughException, NotValidResourceException, UpgradeErrorException {
    if (isBarrackThere(kingdom.getBuildings())) {
      List<Resource> kingdomResources = kingdom.getResources();
      Gold gold = getGold(kingdomResources);
      return purchaseIfPossible(gold, 1L, troopUpgradeCost);
    }
    throw new UpgradeErrorException("There is no barrack.");
  }

  public List<Troop> purchaseTroopUpgrade(Kingdom kingdom, Long lvl, Long amount) throws GoldNotEnoughException, NotValidResourceException, TroopIdNotFoundException, UpgradeErrorException {
    List<Troop> troops = kingdom.getTroops();
    if (troops.stream().anyMatch(t -> t.getLevel().equals(lvl))) {
      List<Troop> matchingLevelTroops = troops
              .stream()
              .filter(t -> t.getLevel().equals(lvl))
              .limit(amount)
              .collect(Collectors.toList());
      List<Resource> kingdomResources = kingdom.getResources();
      Gold gold = getGold(kingdomResources);
      Long upgradeCost = matchingLevelTroops.size() * troopUpgradeCost;
      Long upgradeTo = lvl + 1L;
      purchaseIfPossible(gold, upgradeTo, upgradeCost);
      return matchingLevelTroops;
    }
    throw new UpgradeErrorException("upgrade is not allowed");
  }

  public Resource purchaseBuildingUpgrade(Kingdom kingdom, Long id) throws GoldNotEnoughException, BuildingIdNotFoundException, NotValidResourceException, UpgradeErrorException {
    Building building = buildingService.findById(id);
    List<Building> buildingsOfKingdom = kingdom.getBuildings();
    List<Building> townHallList = buildingsOfKingdom.stream().filter(b -> b instanceof TownHall).collect(Collectors.toList());
    Building townHall = Iterables.getOnlyElement(townHallList);
    List<Resource> kingdomResources = kingdom.getResources();
    Gold gold = getGold(kingdomResources);
    Long upgradeLevel = building.getLevel() + 1L;
    String type = building.getClass().getSimpleName();

    if (type.equals("TownHall") || townHall.getLevel() > building.getLevel()) {
      return purchaseIfPossible(gold, upgradeLevel, buildingUpgradeCost);
    }
    throw new UpgradeErrorException("Building level can not be higher than TownHall level");
  }

  private Gold getGold(List<Resource> kingdomResources) {
    List<Resource> filteredResources = kingdomResources.stream().filter(r -> r instanceof Gold).collect(Collectors.toList());
    return (Gold) Iterables.getOnlyElement(filteredResources);
  }

  private Boolean isGoldEnough(Gold gold, Long upgradeCost) {
    return gold.getAmount() >= upgradeCost;
  }

  private Resource purchaseIfPossible(Gold gold, Long upgradeTo, Long upgradeCost) throws NotValidResourceException, GoldNotEnoughException {
    if (isGoldEnough(gold, upgradeCost)) {
      Long newGoldAmount = gold.getAmount() - upgradeTo * upgradeCost;
      gold.setAmount(newGoldAmount);
      return resourceService.save(Optional.of(gold));
    }
    throw new GoldNotEnoughException("Not enough gold");
  }

  private boolean isBarrackThere(List<Building> kingdomBuildings) {
    return kingdomBuildings.stream().anyMatch(b -> b instanceof Barracks);
  }
}
