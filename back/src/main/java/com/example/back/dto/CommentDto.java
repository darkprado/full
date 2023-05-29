package com.example.back.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class CommentDto {

    @Schema(description = "Идентификатор комментария")
    private Long id;
    @NotEmpty
    @Schema(description = "Сообщение")
    private String message;
    @Schema(description = "Никнейм пользователя")
    private String username;

}
