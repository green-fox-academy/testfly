package com.greenfox.tribes1.Building;

public enum BuildingType {

  Farm {
    public Building makeBuilding() {
      return new Farm();
    }
  },

  Mine {
    public Building makeBuilding() {
      return new Mine();
    }
  },

  Barracks {
    public Building makeBuilding() {
      return new Barracks();
    }
  },

  TownHall {
    public Building makeBuilding() {
      return new TownHall();
    }
  };

  public Building makeBuilding() {
    return null;
  }
}
