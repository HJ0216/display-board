package com.mini_prioject.display_board.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CheckDuplictionIdResponse {
  private String userId;
  private String message;
}
