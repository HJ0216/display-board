package com.mini_prioject.display_board.controller;

import com.mini_prioject.display_board.entity.dto.JoinRequest;
import com.mini_prioject.display_board.entity.dto.JoinResponse;
import com.mini_prioject.display_board.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vi/users")
@RestController
public class UserController {
  private final UserService userService;

  @PostMapping("")
  public ResponseEntity<JoinResponse> create(@RequestBody JoinRequest request){
    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(userService.join(request));
  }

}
