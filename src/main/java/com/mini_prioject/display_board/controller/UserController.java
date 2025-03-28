package com.mini_prioject.display_board.controller;

import com.mini_prioject.display_board.entity.dto.JoinRequest;
import com.mini_prioject.display_board.entity.dto.JoinResponse;
import com.mini_prioject.display_board.service.UserService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@RestController
public class UserController {
  private final UserService userService;

  @PostMapping("")
  public ResponseEntity<JoinResponse> create(@Valid @RequestBody JoinRequest request, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      // 오류 메시지 수집
      Map<String, String> errors = new HashMap<>();
      bindingResult.getFieldErrors().forEach(error ->
          errors.put(error.getField(), error.getDefaultMessage())
      );

      // 400 Bad Request와 함께 오류 메시지 반환
      return ResponseEntity.badRequest().body(
          JoinResponse.builder()
                      .userId(request.getUserId())
                      .message(errors.toString())
                      .build()
      );
    }

    return ResponseEntity.status(HttpStatus.CREATED)
                         .body(userService.join(request));
  }

}
