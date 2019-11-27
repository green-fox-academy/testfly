package com.greenfox.tribes1.Resources;

public enum ResourceType {
  food {

    public Resource generateResource() {
      return new Food();
    }
  },
  
  gold {

    public Resource generateResource() {
      return new Gold();
    }
  };
  
  public Resource generateResource() {
    return null;
  }
}