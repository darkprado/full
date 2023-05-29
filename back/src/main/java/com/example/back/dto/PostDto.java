package com.example.back.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class PostDto {

    @Schema(description = "Идентификатор поста")
    private Long id;
    @NotEmpty
    @Schema(description = "Название поста")
    private String title;
    @NotEmpty
    @Schema(description = "Описание поста")
    private String caption;
    @Schema(description = "Локация поста")
    private String location;
    @Schema(description = "Лайки поста")
    private Integer likes;
    @Schema(description = "Никнейм пользователя")
    private String username;
    @Schema(description = "Список никнеймов пользователей, лайкнувших пост")
    private Set<String> likedUsers;

}
