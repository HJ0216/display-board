package com.mini_prioject.display_board.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.mini_prioject.display_board.entity.User;
import com.mini_prioject.display_board.entity.dto.JoinRequest;
import com.mini_prioject.display_board.entity.dto.JoinResponse;
import com.mini_prioject.display_board.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
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
  UserRepository userRepository;
  @Autowired
  PasswordEncoder passwordEncoder;

  @Test
  @DisplayName("회원가입: 성공")
  void joinSuccessTest() {
    // given
    JoinRequest request = JoinRequest.builder()
                                     .userId("userId")
                                     .password("password123@!")
                                     .email("email@email.com")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    // when
    JoinResponse response = userService.join(request);

    // then
    assertThat(response.getUserId()).isEqualTo(request.getUserId());
    assertThat(response.getMessage()).isEqualTo("user.create.success");
  }

  @Test
  @DisplayName("회원가입: 비밀번호 암호화")
  void passwordEncodingTest(){
    // given
    String rawPassword = "password123@!";
    JoinRequest request = JoinRequest.builder()
                                     .userId("userId")
                                     .password(rawPassword)
                                     .email("email@email.com")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    // when
    JoinResponse response = userService.join(request);
    User savedUser = userRepository.findById(response.getId()).orElseThrow();

    // then
    assertThat(passwordEncoder.matches(rawPassword, savedUser.getPassword())).isTrue();
  }

  @Test
  @DisplayName("중복 userId 확인")
  void duplicateUserIdTest() {
    String userId = "userId";
    // given
    JoinRequest request = JoinRequest.builder()
                                     .userId(userId)
                                     .password("password123@!")
                                     .email("email@email.com")
                                     .nickname("nickname")
                                     .isAdmin(false)
                                     .build();

    JoinResponse response = userService.join(request);

    // when
    boolean result = userService.isUserIdDuplicate(userId);

    // then
    assertThat(result).isTrue();
  }
}