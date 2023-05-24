package com.example.back.dto;

import java.util.Set;

import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class PostDto {

    private Long id;
    private String title;
    private String caption;
    private String location;
    private Integer likes;
    private Set<String> likedUsers;

}
