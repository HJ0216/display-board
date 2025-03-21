package com.mini_prioject.display_board.service;

import com.mini_prioject.display_board.entity.User;
import com.mini_prioject.display_board.entity.dto.SignupRequest;
import com.mini_prioject.display_board.entity.dto.SignupResponse;
import com.mini_prioject.display_board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
  private final UserRepository userRepository;

  public SignupResponse signUp(SignupRequest request) {
    return new SignupResponse();
  }
}
