package com.example.back.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.example.back.annotation.PasswordMatches;
import com.example.back.annotation.ValidEmail;

import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
@PasswordMatches
public class SignupRequest {

    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Please enter your firstname")
    private String firstname;
    @NotEmpty(message = "Please enter your lastname")
    private String lastname;
    @Email(message = "Enter email format")
    @NotBlank(message = "Username is required")
    @ValidEmail
    private String email;
    @Size(min = 6)
    @NotEmpty(message = "Password is required")
    private String password;
    private String confirmPassword;

}
