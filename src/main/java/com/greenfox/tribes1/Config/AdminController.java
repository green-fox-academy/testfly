package com.greenfox.tribes1.Config;

import com.greenfox.tribes1.ApplicationUser.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private ApplicationUserService applicationUserService;

  @Autowired
  public AdminController(ApplicationUserService applicationUserService) {
    this.applicationUserService = applicationUserService;
  }

  @DeleteMapping("/users/{id}")
  ResponseEntity deleteUserById(@PathVariable Long id) {
    return applicationUserService.deleteUser(id);
  }
}
