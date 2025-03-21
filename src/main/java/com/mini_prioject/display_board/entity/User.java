package com.mini_prioject.display_board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true, length = 20)
  private String userId;

  @Column(nullable = false, length = 20)
  private String password;

  @Column(nullable = false, unique = true, length = 50)
  private String email;

  @Column(nullable = false, length = 30)
  private String nickname;

  @Column(nullable = true, length = 255)
  private String profileUrl;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(nullable = true)
  private LocalDateTime deletedAt;

  @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean isAdmin;
}
