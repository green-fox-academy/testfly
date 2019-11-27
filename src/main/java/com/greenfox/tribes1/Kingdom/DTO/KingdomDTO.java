package com.greenfox.tribes1.Kingdom.DTO;

import com.greenfox.tribes1.Building.Building;
import com.greenfox.tribes1.Resources.Resource;
import com.greenfox.tribes1.Troop.Model.Troop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KingdomDTO {
  private Long id;
  private String kingdomName;
  private String applicationUserName;
  private List<Building> buildings;
  private List<Resource> resources;
  private List<Troop> troops;
}
