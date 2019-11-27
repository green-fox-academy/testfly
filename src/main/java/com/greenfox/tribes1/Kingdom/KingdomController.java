package com.greenfox.tribes1.Kingdom;

import com.greenfox.tribes1.Building.Barracks;
import com.greenfox.tribes1.Building.Building;
import com.greenfox.tribes1.Exception.*;
import com.greenfox.tribes1.Progression.ProgressionService;
import com.greenfox.tribes1.Purchase.PurchaseService;
import com.greenfox.tribes1.Troop.Model.Troop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class KingdomController {

  private KingdomService kingdomService;
  private ProgressionService progressionService;
  private PurchaseService purchaseService;

  @Autowired
  public KingdomController(KingdomService kingdomService, ProgressionService progressionService, PurchaseService purchaseService) {
    this.kingdomService = kingdomService;
    this.progressionService = progressionService;
    this.purchaseService = purchaseService;
  }

  @GetMapping("/kingdom")
  public ResponseEntity showKingdom(Authentication authentication) throws NotValidKingdomNameException, TroopIdNotFoundException, BuildingNotValidException, NotValidTypeException, TroopNotValidException, BuildingIdNotFoundException, NotValidResourceException {
    progressionService.checkConstruction();
    Kingdom kingdomByUser = kingdomService.getKindomFromAuth(authentication);
    return ResponseEntity.ok(kingdomService.createKingdomDTOFromKingdom(kingdomByUser));

  }

  @PutMapping("/kingdom")
  public ResponseEntity changeKingdomName(Kingdom kingdom, String newName) {
    return ResponseEntity.ok(kingdomService.createKingdomDTOFromKingdom(kingdom));
  }

  @GetMapping("/kingdom/buildings")
  public ResponseEntity showBuildings(Authentication authentication) {
    return ResponseEntity.ok(kingdomService.getKindomFromAuth(authentication).getBuildings());
  }

  @GetMapping("/kingdom/resources")
  public ResponseEntity showResources(Authentication authentication) {
    return ResponseEntity.ok(kingdomService.getKindomFromAuth(authentication).getResources());
  }

  @PostMapping("/kingdom/buildings")
  public ResponseEntity addBuilding(Authentication authentication, @RequestBody String type) throws Exception {
    Kingdom currentKingdom = kingdomService.getKindomFromAuth(authentication);
    purchaseService.purchaseBuilding(currentKingdom);
    progressionService.saveProgression(progressionService.createProgressionDTOForCreation(currentKingdom, type));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/kingdom/buildings/{id}")
  public ResponseEntity upgradeBuilding(Authentication authentication, @PathVariable Long id) throws Exception {
    Kingdom currentKingdom = kingdomService.getKindomFromAuth(authentication);
    purchaseService.purchaseBuildingUpgrade(currentKingdom, id);
    progressionService.saveProgression(progressionService.createProgressionDTOforBuildingUpgrade(currentKingdom, id));
    return ResponseEntity.ok().build();
  }

  @GetMapping("/kingdom/troops")
  public ResponseEntity showTroops(Authentication authentication) {
    return ResponseEntity.ok(kingdomService.getKindomFromAuth(authentication).getTroops());
  }

  @PostMapping("/kingdom/troop")
  public ResponseEntity addTroop(Authentication authentication, @RequestBody String type) throws Exception {
    Kingdom currentKingdom = kingdomService.getKindomFromAuth(authentication);
    purchaseService.purchaseTroop(currentKingdom);
    progressionService.saveProgression(progressionService.createProgressionDTOForCreation(currentKingdom, type));
    return ResponseEntity.ok().build();
  }

  @PutMapping("/kingdom/troop/{lvl}")
  public ResponseEntity upgradeTroop(Authentication authentication, @PathVariable Long lvl, @RequestBody Long amount) throws TroopIdNotFoundException, GoldNotEnoughException, NotValidResourceException, UpgradeErrorException {
    Kingdom currentKingdom = kingdomService.getKindomFromAuth(authentication);
    List<Troop> matchingLevelTroops = purchaseService.purchaseTroopUpgrade(currentKingdom, lvl, amount);
    return ResponseEntity.ok(matchingLevelTroops);
  }
}