package com.example.back.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Service
public class ResponseErrorValidator {

    public ResponseEntity<Object> mapValidation(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (ObjectError error : result.getAllErrors()) {
                errorMap.put(error.getCode(), error.getDefaultMessage());
            }
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getCode(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}
