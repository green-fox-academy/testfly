package com.greenfox.tribes1.Kingdom;

import com.greenfox.tribes1.Building.Building;
import com.greenfox.tribes1.Building.BuildingFactory;
import com.greenfox.tribes1.Building.BuildingType;
import com.greenfox.tribes1.Exception.NotValidKingdomNameException;
import com.greenfox.tribes1.Kingdom.DTO.KingdomDTO;
import com.greenfox.tribes1.Resources.Resource;
import com.greenfox.tribes1.Resources.ResourceFactory;
import com.greenfox.tribes1.Resources.ResourceType;
import com.greenfox.tribes1.Security.Model.UserContext;
import com.greenfox.tribes1.Troop.Model.Troop;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KingdomService {

  private KingdomRepository kingdomRepository;

  @Autowired
  public KingdomService(KingdomRepository kingdomRepository) {
    this.kingdomRepository = kingdomRepository;
  }

  // TODO: Need refactor! We aren't using this method, only in old tests!
  public Kingdom saveKingdom(Kingdom kingdom) throws NotValidKingdomNameException {
    if (kingdom.getName() == null) {
      throw new NotValidKingdomNameException("The given name wasn't correct, or the field is empty!");
    }
    return kingdomRepository.save(kingdom);
  }

  public Kingdom findKingdomByApplicationUserName(String applicationUserName) {
    return kingdomRepository.findKingdomByApplicationUser_Username(applicationUserName);
  }

  public KingdomDTO createKingdomDTOFromKingdom(Kingdom kingdom) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(kingdom, KingdomDTO.class);
  }

  public List<Kingdom> findAll() {
    return kingdomRepository.findAll();
  }

  public void setStarterTroops(Kingdom kingdom) {
    List<Troop> troops = new ArrayList<>();
    kingdom.setTroops(troops);
  }

  public void setStarterBuildings(Kingdom kingdom) {
    List<Building> buildings = createBuildings();
    setKingdomForBuildings(buildings, kingdom);
    kingdom.setBuildings(buildings);
  }

  private List<Building> createBuildings() {
    List<Building> buildings = new ArrayList<>();
    for (BuildingType type : BuildingType.values()) {
      buildings.add(BuildingFactory.createBuilding(type));
    }
    return buildings;
  }

  private void setKingdomForBuildings(List<Building> buildings, Kingdom kingdom) {
    for (Building building : buildings) {
      building.setKingdom(kingdom);
    }
  }

  public void setStarterResource(Kingdom kingdom) {
    List<Resource> resources = createResources();
    setKingdomForResources(resources, kingdom);
    kingdom.setResources(resources);
  }

  private List<Resource> createResources() {
    List<Resource> resources = new ArrayList<>();
    for (ResourceType type : ResourceType.values()) {
      resources.add(ResourceFactory.createResource(type));
    }
    return resources;
  }

  private void setKingdomForResources(List<Resource> resources, Kingdom kingdom) {
    for (Resource resource : resources) {
      resource.setKingdom(kingdom);
    }
  }

  public Kingdom getKindomFromAuth(Authentication authentication){
    UserContext userContext = (UserContext) authentication.getPrincipal();
    return findKingdomByApplicationUserName(userContext.getUsername());
  }
}
