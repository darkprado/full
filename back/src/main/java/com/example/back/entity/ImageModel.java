package com.example.back.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author s.melekhin
 * @since 27 март 2023 г.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_images")
public class ImageModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Lob
    @Column(columnDefinition = "bytea")
    private byte[] imageBytes;
    @JsonIgnore
    private Long userId;
    @JsonIgnore
    private Long postId;

}
