package com.greenfox.tribes1.Troop;

import com.greenfox.tribes1.Troop.Model.SingleTroop;
import com.greenfox.tribes1.Troop.Model.Troop;

public enum TroopType {

  troop {
    @Override
    public Troop makeTroop() {
      return new SingleTroop();
    }
  };

  public Troop makeTroop() {
    return null;
  }
}
