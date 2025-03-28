package com.mini_prioject.display_board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mini_prioject.display_board.entity.dto.JoinRequest;
import com.mini_prioject.display_board.entity.dto.JoinResponse;
import com.mini_prioject.display_board.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WithMockUser(username = "testUser", roles = "USER")
@WebMvcTest(UserController.class)
class UserControllerTest {
  @MockitoBean
  private UserService userService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  WebApplicationContext webApplicationContext;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .defaultRequest(post("/**").with(csrf()))
        .defaultRequest(put("/**").with(csrf()))
        .defaultRequest(delete("/**").with(csrf()))
        .build();
  }

  @Test
  @DisplayName("회원가입 성공 테스트")
  void createUserSuccessTest() throws Exception {
    // given
    JoinRequest request = JoinRequest.builder()
                                     .userId("testUser")
                                     .password("password123@!")
                                     .email("test@example.com")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    JoinResponse response = JoinResponse.builder()
                                        .userId("testUser")
                                        .message("user.create.success")
                                        .build();

    given(userService.join(any(JoinRequest.class))).willReturn(response);

    // when & then
    mockMvc.perform(post("/api/v1/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isCreated())
           .andExpect(jsonPath("$.userId").value("testUser"))
           .andExpect(jsonPath("$.message").value("user.create.success"));
  }
}