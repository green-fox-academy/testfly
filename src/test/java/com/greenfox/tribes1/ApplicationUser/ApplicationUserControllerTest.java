package com.greenfox.tribes1.ApplicationUser;

import com.greenfox.tribes1.ApplicationUser.DTO.ApplicationUserDTO;
import com.greenfox.tribes1.ApplicationUser.DTO.ApplicationUserWithKingdomDTO;
import com.greenfox.tribes1.Kingdom.Kingdom;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationUserControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  ApplicationUserService applicationUserService;

  private String username = "testUser";
  private String password = "password";
  private String kingdomName = "testKingdom";
  private String error = "error";

  @Test
  public void register_unsuccessful_withMissingJsonObject() throws Exception {
    mockMvc.perform(post("/register"))
            .andExpect(status().isBadRequest());
  }

  @Test
  public void register_withMissingUsername() throws Exception {

    String jsonTestUserMissingPassword = new JSONObject()
            .put("username", "")
            .put("password", password)
            .put("kingdomName", kingdomName)
            .toString();

    String result = new JSONObject()
            .put("status", error)
            .put("message", "Missing parameter(s): {username=must not be blank}")
            .toString();

    mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonTestUserMissingPassword))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(result));
  }

  @Test
  public void register_withMissingPassword() throws Exception {

    String jsonTestUserMissingUsername = new JSONObject()
            .put("username", username)
            .put("password", "")
            .put("kingdomName", kingdomName)
            .toString();

    String result = new JSONObject()
            .put("status", error)
            .put("message", "Missing parameter(s): {password=must not be blank}")
            .toString();

    mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonTestUserMissingUsername))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(result));
  }

  @Test
  public void register_successful() throws Exception {
    Kingdom kingdom = Kingdom.builder()
            .id(1L)
            .name(kingdomName)
            .build();

    ApplicationUser testUser = ApplicationUser.builder()
            .id(1L)
            .username(username)
            .password(password)
            .kingdom(kingdom)
            .build();

    ModelMapper modelMapper = new ModelMapper();
    ApplicationUserWithKingdomDTO testUserDTOWithKingdom = modelMapper.map(testUser, ApplicationUserWithKingdomDTO.class);

    String jsonTestUser = new JSONObject()
            .put("username", username)
            .put("password", password)
            .put("kingdomName", kingdomName)
            .toString();

    String result = new JSONObject()
            .put("id", 1)
            .put("username", username)
            .put("kingdomId", 1)
            .toString();

    when(applicationUserService.registerNewUser(any(ApplicationUserDTO.class))).thenReturn(testUser);
    when(applicationUserService.createDTOwithKingdomfromUser(testUser)).thenReturn(testUserDTOWithKingdom);

    mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonTestUser))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(content().json(result));

    verify(applicationUserService, times(1)).registerNewUser(any(ApplicationUserDTO.class));
    verify(applicationUserService, times(1)).createDTOwithKingdomfromUser(testUser);
    verifyNoMoreInteractions(applicationUserService);
  }
}
