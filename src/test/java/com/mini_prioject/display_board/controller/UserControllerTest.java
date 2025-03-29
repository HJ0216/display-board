package com.mini_prioject.display_board.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
  @DisplayName("회원가입 성공")
  void createUser_WhenValidRequest() throws Exception {
    // given
    JoinRequest request = JoinRequest.builder()
                                     .userId("testUser")
                                     .password("passwOrd123@!")
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

  @Test
  @DisplayName("회원가입 실패: 비밀번호 유효성 검사 실패")
  void createUser_WhenInvalidPassword() throws Exception {
    // given
    JoinRequest request = JoinRequest.builder()
                                     .userId("testUser")
                                     .password("12345678")
                                     .email("test@example.com")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    JoinResponse response = JoinResponse.builder()
                                        .userId("testUser")
                                        .message("user.create.fail")
                                        .build();

    given(userService.join(request)).willReturn(response);

    // when & then
    mockMvc.perform(post("/api/v1/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.userId").value("testUser"))
           .andExpect(jsonPath("$.message").value("{password=비밀번호는 숫자, 소문자, 대문자, 특수문자를 포함해야 합니다}"));
  }

  @Test
  @DisplayName("회원가입 실패: 이메일 유효성 검사 실패")
  void createUser_WhenInvalidEmail() throws Exception {
    // given
    JoinRequest request = JoinRequest.builder()
                                     .userId("testUser")
                                     .password("passwOrd123@!")
                                     .email("test")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    JoinResponse response = JoinResponse.builder()
                                        .userId("testUser")
                                        .message("user.create.fail")
                                        .build();

    given(userService.join(request)).willReturn(response);

    // when & then
    mockMvc.perform(post("/api/v1/users")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(request)))
           .andExpect(status().isBadRequest())
           .andExpect(jsonPath("$.userId").value("testUser"))
           .andExpect(jsonPath("$.message").value("{email=이메일 형식이 올바르지 않습니다.}"));
  }

  @Test
  @DisplayName("User ID 중복 검사: 중복 X")
  void checkDuplicateUser_WhenDifferentUserId() throws Exception {
    // given
    String userId = "testUser";

    given(userService.isUserIdDuplicate(userId)).willReturn(false);

    // when & then
    mockMvc.perform(get("/api/v1/users/check-id-duplication")
               .param("userId", "testUser"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.userId").value("testUser"))
           .andExpect(jsonPath("$.message").value("user.check-id-duplication.success"));
  }

  @Test
  @DisplayName("User ID 중복 검사: 중복 O")
  void checkDuplicateUser_WhenSameUserId() throws Exception {
    // given
    String userId = "testUser";

    given(userService.isUserIdDuplicate(userId)).willReturn(true);

    // when & then
    mockMvc.perform(get("/api/v1/users/check-id-duplication")
               .param("userId", "testUser"))
           .andExpect(status().isConflict())
           .andExpect(jsonPath("$.userId").value("testUser"))
           .andExpect(jsonPath("$.message").value("user.check-id-duplication.fail"));
  }
}