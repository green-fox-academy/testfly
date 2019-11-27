package com.greenfox.tribes1.ApplicationUser;

import com.greenfox.tribes1.ApplicationUser.DTO.ApplicationUserDTO;
import com.greenfox.tribes1.Exception.UsernameTakenException;
import com.greenfox.tribes1.Kingdom.KingdomService;
import com.greenfox.tribes1.Role.RoleRepository;
import com.greenfox.tribes1.Role.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ApplicationUserServiceTest {

  private String username = "John";
  private String password = "password";
  private ApplicationUserService applicationUserService;

  @Mock
  private BCryptPasswordEncoder encoder;

  @Mock
  ApplicationUserRepository applicationUserRepository;

  @Mock
  KingdomService kingdomService;

  @Mock
  RoleService roleService;

  private ApplicationUserDTO testUserDTO = ApplicationUserDTO.builder()
          .username(username)
          .password(password)
          .build();
  private ApplicationUser testUser = ApplicationUser.builder()
          .username(username)
          .password(password)
          .build();

  @Before
  public void init() {
    MockitoAnnotations.initMocks(this);
    applicationUserService = new ApplicationUserService(applicationUserRepository, encoder, kingdomService, roleService);
  }

  @Test
  public void getByUsername_test() {
    when(applicationUserRepository.findByUsername(username)).thenReturn(Optional.of(testUser));
    assertEquals(testUser, applicationUserService.getByUsername(username).orElse(null));
  }

  @Test(expected = UsernameTakenException.class)
  public void registerNewUser_ThrowException_IfUserAlreadyExist() throws Exception {
    when(applicationUserRepository.existsByUsername(username)).thenReturn(true);
    applicationUserService.registerNewUser(testUserDTO);
  }

  @Test
  public void registerNewUser_ReturnsUser_IfUserNotExist() throws Exception {
    when(applicationUserRepository.save(Mockito.any(ApplicationUser.class))).thenReturn(testUser);
    assertEquals(testUser, applicationUserService.registerNewUser(testUserDTO));
  }

  @Test
  public void createUserFromDTO_test() {
    assertEquals(testUser.getUsername(), applicationUserService.createUserFromDTO(testUserDTO).getUsername());
    assertEquals(testUser.getPassword(), applicationUserService.createUserFromDTO(testUserDTO).getPassword());
  }
}
