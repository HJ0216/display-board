package com.mini_prioject.display_board.repository;

import com.mini_prioject.display_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
