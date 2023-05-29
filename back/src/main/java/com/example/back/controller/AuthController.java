package com.example.back.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.back.config.AppProps;
import com.example.back.payload.request.LoginRequest;
import com.example.back.payload.request.SignupRequest;
import com.example.back.payload.response.JWTTokenSuccessResponse;
import com.example.back.payload.response.MessageResponse;
import com.example.back.security.JWTTokenProvider;
import com.example.back.service.UserService;
import com.example.back.validation.ResponseErrorValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@CrossOrigin
@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
@RequiredArgsConstructor
public class AuthController {

    private final ResponseErrorValidator errorValidator;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final AppProps appProps;

    @Operation(
            operationId = "Регистрация", description = "Регистрация пользователя",
            parameters = { @Parameter(name = "request", description = "Данные пользователя") }
    )
    @PostMapping("/signup")
    public ResponseEntity<Object> signup(@Valid @RequestBody SignupRequest request, BindingResult bindingResult) {
        ResponseEntity<Object> errors = errorValidator.mapValidation(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        userService.save(request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

    @Operation(
            operationId = "Авторизация", description = "Авторизация пользователя",
            parameters = { @Parameter(name = "request", description = "Данные пользователя") }
    )
    @PostMapping("/signin")
    public ResponseEntity<Object> signin(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        ResponseEntity<Object> errors = errorValidator.mapValidation(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = appProps.getTokenPrefix() + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

}
