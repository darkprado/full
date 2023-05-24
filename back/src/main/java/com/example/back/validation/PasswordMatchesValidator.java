package com.example.back.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.back.annotation.PasswordMatches;
import com.example.back.annotation.ValidEmail;
import com.example.back.payload.request.SignupRequest;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        SignupRequest request = (SignupRequest) obj;
        return request.getPassword().equals(request.getConfirmPassword());
    }
}
