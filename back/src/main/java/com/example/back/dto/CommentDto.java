package com.example.back.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
public class CommentDto {

    private Long id;
    @NotEmpty
    private String message;
    private String username;

}
