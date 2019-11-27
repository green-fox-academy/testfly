package com.greenfox.tribes1.ApplicationUser.DTO;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationUserWithKingdomDTO {

  private Long id;
  private String username;
  private Long kingdomId;
}
