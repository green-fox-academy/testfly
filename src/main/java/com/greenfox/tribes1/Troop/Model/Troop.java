package com.greenfox.tribes1.Troop.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.greenfox.tribes1.Kingdom.Kingdom;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "troop_type")
@NoArgsConstructor
@Getter
@Setter
public abstract class Troop {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotNull
  @PositiveOrZero
  private Long level;
  private Float HP;
  private Long attack;
  private Long defense;
  private Timestamp started_at;
  private Timestamp finished_at;

  @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinColumn(name = "kingdom_id", referencedColumnName = "id")
  @JsonBackReference
  private Kingdom kingdom;

  public void levelUp(){
    setLevel(getLevel()+1L);
    setHP(getHP() * 1.1F);
    setAttack(getAttack() * 5);
    setHP(getHP() * 5);
  }

}
