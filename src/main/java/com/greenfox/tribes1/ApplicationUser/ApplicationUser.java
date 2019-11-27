package com.greenfox.tribes1.ApplicationUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenfox.tribes1.Kingdom.Kingdom;
import com.greenfox.tribes1.Role.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationUser {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String username;
  @JsonIgnore
  private String password;
  private String userEmail;
  
  @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
  @JoinColumn(name = "kingdom_id")
  private Kingdom kingdom;

  @ManyToMany(cascade = {CascadeType.PERSIST}, fetch = FetchType.EAGER)
  @JoinTable(
          name = "application_users_roles",
          joinColumns = @JoinColumn(
                  name = "application_user_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(
                  name = "role_id", referencedColumnName = "id"))
  private Set<Role> roles;
  
  public ApplicationUser(String username, String password, String userEmail) {
    this.username = username;
    this.password = password;
    this.userEmail = userEmail;
  }
}
