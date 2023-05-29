package com.example.back.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.back.dto.UserDto;
import com.example.back.mapper.UserMapper;
import com.example.back.service.UserService;
import com.example.back.validation.ResponseErrorValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 25 май 2023 г.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ResponseErrorValidator validator;
    private final UserMapper userMapper;

    @Operation(
            operationId = "Получить текущего пользователя", description = "Получает текущего пользователя")
    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser(Principal principal) {
        return new ResponseEntity<>(userMapper.toDto(userService.getCurrentUser(principal)), HttpStatus.OK);
    }

    @Operation(
            operationId = "Получить пользователя по идентификатору", description = "Получает пользователя по идентификатору",
            parameters = { @Parameter(name = "userId", description = "идентификатор пользователя") }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable("userId") String userId) {
        return new ResponseEntity<>(userMapper.toDto(userService.getById(Long.parseLong(userId))), HttpStatus.OK);
    }

    @Operation(
            operationId = "Обновить текущего пользователя", description = "Обновляет текущего пользователя")
    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = validator.mapValidation(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        return new ResponseEntity<>(userMapper.toDto(userService.update(userDto, principal)), HttpStatus.OK);
    }

}
