package com.example.back.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.back.entity.ImageModel;

/**
 * @author s.melekhin
 * @since 27 март 2023 г.
 */
@Repository
public interface ImageModelDao extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserId(Long userId);

    Optional<ImageModel> findByPostId(Long postId);

}
