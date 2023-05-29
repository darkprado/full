package com.example.back.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.Comment;
import com.example.back.entity.Post;

import io.swagger.v3.oas.annotations.Hidden;

/**
 * @author s.melekhin
 * @since 27 март 2023 г.
 */
@Hidden
@Repository
public interface CommentDao extends JpaRepository<Comment, Long> {

    List<Comment> findAllByPost(Post post);

    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);

}
