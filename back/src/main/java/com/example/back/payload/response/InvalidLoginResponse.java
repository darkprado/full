package com.example.back.payload.response;

import lombok.Getter;

/**
 * @author s.melekhin
 * @since 04 апр. 2023 г.
 */
@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }

}
