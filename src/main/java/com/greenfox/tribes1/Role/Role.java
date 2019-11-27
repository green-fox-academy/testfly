package com.greenfox.tribes1.Role;

import com.greenfox.tribes1.ApplicationUser.ApplicationUser;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(name = "role")
  protected RoleType roleType;

  @ManyToMany(mappedBy = "roles")
  private List<ApplicationUser> users;
}
