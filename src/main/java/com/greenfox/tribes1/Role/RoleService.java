package com.greenfox.tribes1.Role;

import com.greenfox.tribes1.Exception.RoleNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

  private RoleRepository roleRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  public Role findByRoleType(RoleType roleType) throws RoleNotExistsException {
    return roleRepository.findByRoleType(roleType).orElseThrow(() -> new RoleNotExistsException("No such role"));
  }
}
