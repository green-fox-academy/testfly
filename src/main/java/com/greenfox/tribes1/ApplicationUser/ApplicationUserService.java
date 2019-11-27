package com.greenfox.tribes1.ApplicationUser;

import com.greenfox.tribes1.ApplicationUser.DTO.ApplicationUserDTO;
import com.greenfox.tribes1.ApplicationUser.DTO.ApplicationUserWithKingdomDTO;
import com.greenfox.tribes1.Exception.RoleNotExistsException;
import com.greenfox.tribes1.Exception.UsernameTakenException;
import com.greenfox.tribes1.Kingdom.Kingdom;
import com.greenfox.tribes1.Kingdom.KingdomService;
import com.greenfox.tribes1.Role.Role;
import com.greenfox.tribes1.Role.RoleService;
import com.greenfox.tribes1.Role.RoleType;
import com.greenfox.tribes1.Security.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service

public class ApplicationUserService implements UserService {

  private ApplicationUserRepository applicationUserRepository;
  private BCryptPasswordEncoder encoder;
  private KingdomService kingdomService;
  private RoleService roleService;

  @Autowired
  public ApplicationUserService(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder encoder, KingdomService kingdomService, RoleService roleService) {
    this.applicationUserRepository = applicationUserRepository;
    this.encoder = encoder;
    this.kingdomService = kingdomService;
    this.roleService = roleService;
  }

  @Override
  public Optional<ApplicationUser> getByUsername(String username) {
    return this.applicationUserRepository.findByUsername(username);
  }

  public ApplicationUser registerNewUser(ApplicationUserDTO applicationUserDTO) throws RoleNotExistsException, UsernameTakenException {
    String userName = applicationUserDTO.getUsername();

    if (!applicationUserRepository.existsByUsername(userName)) {
      String kingdomName = applicationUserDTO.getKingdomName();

      ApplicationUser userToBeSaved = createUserFromDTO(applicationUserDTO);
      userToBeSaved.setPassword(encoder.encode(applicationUserDTO.getPassword()));

      Kingdom kingdom = createKingdom(kingdomName, userName);
      kingdom.setApplicationUser(userToBeSaved);

      userToBeSaved.setKingdom(kingdom);

      Role memberRole = roleService.findByRoleType(RoleType.MEMBER);
      Set<Role> roles = new HashSet<>();
      roles.add(memberRole);
      userToBeSaved.setRoles(roles);

      kingdomService.setStarterBuildings(kingdom);
      kingdomService.setStarterResource(kingdom);
      kingdomService.setStarterTroops(kingdom);

      return applicationUserRepository.save(userToBeSaved);
    }
    throw new UsernameTakenException("Username already taken, please choose an other one.");
  }

  private Kingdom createKingdom(String kingdomName, String username) {
    if (isKingdomNameNullOrEmpty(kingdomName)) {
      return new Kingdom(String.format("%s's kingdom", username));
    }
    return new Kingdom(kingdomName);
  }

  public ApplicationUser createUserFromDTO(ApplicationUserDTO applicationUserDTO) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(applicationUserDTO, ApplicationUser.class);
  }

  public ApplicationUserWithKingdomDTO createDTOwithKingdomfromUser(ApplicationUser applicationUser) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper.map(applicationUser, ApplicationUserWithKingdomDTO.class);
  }

  private Boolean isKingdomNameNullOrEmpty(String kingdomName) {
    return kingdomName == null || kingdomName.isEmpty();
  }

  public ResponseEntity deleteUser(Long id) {
    if (applicationUserRepository.existsById(id)) {
      applicationUserRepository.deleteById(id);
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.status(204).build();
  }
}
