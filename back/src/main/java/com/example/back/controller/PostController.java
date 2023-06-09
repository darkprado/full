package com.example.back.controller;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.back.dto.PostDto;
import com.example.back.mapper.PostMapper;
import com.example.back.payload.response.MessageResponse;
import com.example.back.service.PostService;
import com.example.back.validation.ResponseErrorValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 25 май 2023 г.
 */
@RestController
@RequestMapping("/post")
@CrossOrigin
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ResponseErrorValidator validator;
    private final PostMapper postMapper;

    @Operation(
            operationId = "Создать пост", description = "Создает пост",
            parameters = { @Parameter(name = "postDto", description = "Информация по посту") })
    @PostMapping("/create")
    public ResponseEntity<Object> createPost(@Valid @RequestBody PostDto postDto, BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = validator.mapValidation(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        return new ResponseEntity<>(postMapper.toDto(postService.save(postDto, principal)), HttpStatus.OK);
    }

    @Operation(
            operationId = "Получить все посты", description = "Получает все посты")
    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDTOList = postService.getAll()
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @Operation(
            operationId = "Получить все посты пользователя", description = "Получает все посты пользователя")
    @GetMapping("/user/posts")
    public ResponseEntity<List<PostDto>> getAllPostsForUser(Principal principal) {
        List<PostDto> postDTOList = postService.getAllByUser(principal)
                .stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList, HttpStatus.OK);
    }

    @Operation(
            operationId = "Лайкнуть/дизлайкнуть пост", description = "Лайкает/дизлайкает пост",
            parameters = { @Parameter(name = "postId", description = "Идентификатор поста"),
                    @Parameter(name = "username", description = "Никнейм") })
    @PostMapping("/{postId}/{username}/like")
    public ResponseEntity<PostDto> likePost(@PathVariable("postId") String postId, @PathVariable("username") String username) {
        return new ResponseEntity<>(postMapper.toDto(postService.like(Long.parseLong(postId), username)), HttpStatus.OK);
    }

    @Operation(
            operationId = "Удалить пост", description = "Удаляет пост",
            parameters = { @Parameter(name = "postId", description = "Идентификатор поста"),
                    @Parameter(name = "username", description = "Никнейм") })
    @DeleteMapping("/{postId}/delete")
    public ResponseEntity<MessageResponse> deletePost(@PathVariable("postId") String postId, Principal principal) {
        postService.delete(Long.parseLong(postId), principal);
        return new ResponseEntity<>(new MessageResponse(String.format("Post with id %s was deleted", postId)), HttpStatus.OK);
    }

}
