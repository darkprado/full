package com.example.back.dto;

import javax.validation.constraints.NotEmpty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class UserDto {

    @Schema(description = "Индентификатор пользователя")
    private Long id;
    @Schema(description = "Имя пользователя")
    @NotEmpty
    private String firstname;
    @Schema(description = "Фамилия пользователя")
    @NotEmpty
    private String lastname;
    @Schema(description = "Никнейм пользователя")
    private String username;
    @Schema(description = "Биография пользователя")
    private String bio;

}
