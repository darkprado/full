package com.example.back.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Post;
import com.example.back.entity.User;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * @author s.melekhin
 * @since 27 март 2023 г.
 */
@Hidden
@Repository
public interface PostDao extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByCreatedDateDesc(User user);

    List<Post> findAllByOrderByCreatedDateDesc();

    Optional<Post> findPostByIdAndUser(Long id, User user);

}
