package com.example.back.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.User;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * @author s.melekhin
 * @since 27 март 2023 г.
 */
@Hidden
@Repository
public interface UserDao extends JpaRepository<User, Long> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long userId);

}
