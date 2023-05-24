package com.example.back.payload.request;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class LoginRequest {

    @NotEmpty(message = "Username can't be empty")
    private String username;
    @NotEmpty(message = "Password can't be empty")
    private String password;

}
