package com.greenfox.tribes1.ApplicationUser.DTO;

import lombok.*;
import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApplicationUserDTO {

  @NotBlank
  private String username;
  @NotBlank
  private String password;
  private String kingdomName;

}
