package com.example.back.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.back.annotation.PasswordMatches;
import com.example.back.annotation.ValidEmail;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
@PasswordMatches
public class SignupRequest {

    @NotEmpty(message = "Username is required")
    @Schema(description = "Никнейм пользователя")
    private String username;
    @Schema(description = "Имя пользователя")
    @NotEmpty(message = "Please enter your firstname")
    private String firstname;
    @Schema(description = "Фамилия пользователя")
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @Schema(description = "Email пользователя")
    @Email(message = "Enter email format")
    @NotBlank(message = "Email is required")
    @ValidEmail
    private String email;
    @Schema(description = "Пароль пользователя")
    @Size(min = 6)
    @NotEmpty(message = "Password is required")
    private String password;
    @Schema(description = "Повтор пароля пользователя")
    private String confirmPassword;

}
