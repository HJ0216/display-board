package com.mini_prioject.display_board.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JoinRequest {
  private String userId;
  private String password;
  private String email;
  private String nickname;
  private Boolean isAdmin;
}
