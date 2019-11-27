package com.greenfox.tribes1.Building;

public class BuildingFactory {

  public static Building createBuilding(BuildingType buildingType) {
    return buildingType.makeBuilding();
  }
}
