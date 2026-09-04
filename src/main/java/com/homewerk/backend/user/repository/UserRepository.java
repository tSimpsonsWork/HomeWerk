package com.homewerk.backend.user.repository;

import com.homewerk.backend.user.enums.UserRole;
import com.homewerk.backend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByRole(UserRole role);

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);
}