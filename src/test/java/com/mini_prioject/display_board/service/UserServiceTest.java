package com.mini_prioject.display_board.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.mini_prioject.display_board.entity.dto.JoinRequest;
import com.mini_prioject.display_board.entity.dto.JoinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceTest {
  @Autowired
  UserService userService;
  @Autowired
  PasswordEncoder passwordEncoder;


  @Test
  @DisplayName("회원가입 성공 테스트")
  void joinSuccess() {
    // Given
    JoinRequest request = JoinRequest.builder()
                                     .userId("userId")
                                     .password("12345678")
                                     .email("email@email.com")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    // When
    JoinResponse response = userService.join(request);

    // Then
    assertThat(response.getUserId()).isEqualTo(request.getUserId());
    assertThat(response.getMessage()).isEqualTo("user.create.success");
  }

}