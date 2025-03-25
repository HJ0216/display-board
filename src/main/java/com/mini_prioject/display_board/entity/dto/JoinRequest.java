package com.mini_prioject.display_board.entity.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Builder
public class JoinRequest {
  private String userId;
  @NotNull
  @Size(min = 8, max = 20, message = "비밀번호는 8자 이상이어야 합니다")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
      message = "비밀번호는 숫자, 소문자, 대문자, 특수문자를 포함해야 합니다")
  private String password;
  private String email;
  private String nickname;
  private Boolean isAdmin;
}
