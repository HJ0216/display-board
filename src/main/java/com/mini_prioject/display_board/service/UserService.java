package com.mini_prioject.display_board.service;

import com.mini_prioject.display_board.entity.User;
import com.mini_prioject.display_board.entity.dto.JoinRequest;
import com.mini_prioject.display_board.entity.dto.JoinResponse;
import com.mini_prioject.display_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public JoinResponse join(JoinRequest request) {
    String encryptedPassword = passwordEncoder.encode(request.getPassword());

    User user = User.builder()
                    .userId(request.getUserId())
                    .password(encryptedPassword)
                    .email(request.getEmail())
                    .nickname(request.getNickname())
                    .isAdmin(request.getIsAdmin())
                    .build();

    return JoinResponse.builder()
                       .userId(userRepository.save(user).getUserId())
                       .message("user.create.success")
                       .build();
  }
}
