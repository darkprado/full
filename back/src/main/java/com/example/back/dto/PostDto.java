package com.example.back.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class PostDto {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String caption;
    private String location;
    private Integer likes;
    private String username;
    private Set<String> likedUsers;

}
