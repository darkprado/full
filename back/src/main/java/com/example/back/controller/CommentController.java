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

import com.example.back.dto.CommentDto;
import com.example.back.mapper.CommentMapper;
import com.example.back.payload.response.MessageResponse;
import com.example.back.service.CommentService;
import com.example.back.validation.ResponseErrorValidator;

import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 25 май 2023 г.
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ResponseErrorValidator validator;
    private final CommentMapper commentMapper;

    @PostMapping("/{postId}/create")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentDto commentDto, @PathVariable("postId") String postId,
            BindingResult bindingResult, Principal principal) {
        ResponseEntity<Object> errors = validator.mapValidation(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) {
            return errors;
        }
        return new ResponseEntity<>(commentMapper.toDto(commentService.save(Long.parseLong(postId), commentDto, principal)), HttpStatus.OK);
    }

    @GetMapping("/{postId}/all")
    public ResponseEntity<List<CommentDto>> getAllCommentsToPost(@PathVariable("postId") String postId) {
        List<CommentDto> commentDTOList = commentService.getAllForPost(Long.parseLong(postId))
                .stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<MessageResponse> deleteComment(@PathVariable("commentId") String commentId) {
        commentService.delete(Long.parseLong(commentId));
        return new ResponseEntity<>(new MessageResponse(String.format("Comment with id %s was deleted", commentId)), HttpStatus.OK);
    }

}
