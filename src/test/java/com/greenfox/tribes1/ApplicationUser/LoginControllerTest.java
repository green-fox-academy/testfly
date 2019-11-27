package com.greenfox.tribes1.ApplicationUser;

import com.greenfox.tribes1.Security.JWT.Extractor.JwtHeaderTokenExtractor;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:beforeTestRun.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:afterTestRun.sql")
})
public class LoginControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  JwtHeaderTokenExtractor jwtHeaderTokenExtractor;

  @Before
  public void init() throws JSONException {
    jwtHeaderTokenExtractor = new JwtHeaderTokenExtractor();
  }

  @Test
  public void login_successful_registereUser() throws Exception {

    String jsonTestUser = new JSONObject()
            .put("username", "testUser1")
            .put("password", "pass1")
            .toString();

    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonTestUser)
    )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.token").exists())
            .andExpect(jsonPath("$.token").isNotEmpty())
            .andExpect(jsonPath("$.token").isString())
            .andExpect(jsonPath("$.refreshToken").exists())
            .andExpect(jsonPath("$.refreshToken").isNotEmpty())
            .andExpect(jsonPath("$.refreshToken").isString());
  }

  @Test
  public void login_unsuccessful_notRegisteredUser() throws Exception {

    String jsonNotRegisteredUser = new JSONObject()
            .put("username", "testUser4")
            .put("password", "pass4")
            .toString();

    String jsonErrorNoSuchUser = new JSONObject()
            .put("status", "error")
            .put("message", "No such user: testUser4")
            .toString();

    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonNotRegisteredUser)
    )
            .andExpect(status().is(401))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(jsonErrorNoSuchUser));
  }

  @Test
  public void login_unsuccessful_wrongPassword() throws Exception {

    String jsonTestUserWrongPassword = new JSONObject()
            .put("username", "testUser1")
            .put("password", "wrongPassword")
            .toString();

    String jsonErrorWrongPassword = new JSONObject()
            .put("status", "error")
            .put("message", "Wrong password!")
            .toString();

    mockMvc.perform(post("/login")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(jsonTestUserWrongPassword)
    )
            .andExpect(status().is(401))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(jsonErrorWrongPassword));
  }
}
