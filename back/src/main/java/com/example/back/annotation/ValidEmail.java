package com.example.back.annotation;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.example.back.validation.EmailValidator;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {

    String message() default "Invalid email";

    Class<?>[] groups() default{};

    Class<? extends Payload>[] payload() default {};

}
