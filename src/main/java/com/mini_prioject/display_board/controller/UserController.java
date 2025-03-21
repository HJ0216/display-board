package com.mini_prioject.display_board.controller;

import com.mini_prioject.display_board.entity.dto.SignupRequest;
import com.mini_prioject.display_board.entity.dto.SignupResponse;
import com.mini_prioject.display_board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
  private final UserService userService;

  @PostMapping("")
  public SignupResponse signup(@RequestBody SignupRequest request){
    SignupResponse response = userService.signUp(request);
  }

}
