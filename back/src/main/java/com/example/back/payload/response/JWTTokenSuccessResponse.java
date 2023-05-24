package com.example.back.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Data
@AllArgsConstructor
public class JWTTokenSuccessResponse {

   private boolean success;
   private String token;

}
