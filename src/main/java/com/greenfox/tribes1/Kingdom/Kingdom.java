package com.greenfox.tribes1.Kingdom;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.greenfox.tribes1.ApplicationUser.ApplicationUser;
import com.greenfox.tribes1.Building.Building;
import com.greenfox.tribes1.Resources.Resource;
import com.greenfox.tribes1.Troop.Model.Troop;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Kingdom {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  @OneToMany(mappedBy = "kingdom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER)
  @JsonManagedReference
  private List<Resource> resources;

  @OneToOne(mappedBy = "kingdom", cascade = CascadeType.PERSIST)
  ApplicationUser applicationUser;

  @OneToMany(mappedBy = "kingdom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JsonManagedReference
  private List<Troop> troops;

  @OneToMany(mappedBy = "kingdom", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JsonManagedReference
  private List<Building> buildings;

  public Kingdom(String name) {
    this.name = name;
  }
}
