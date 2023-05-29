package com.example.back.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.back.entity.ImageModel;
import com.example.back.payload.response.MessageResponse;
import com.example.back.service.ImageUploadService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;

/**
 * @author s.melekhin
 * @since 25 май 2023 г.
 */
@RestController
@RequestMapping("/image")
@CrossOrigin
@RequiredArgsConstructor
public class ImageController {

    private final ImageUploadService imageUploadService;

    @Operation(
            operationId = "Сохранить картинку пользователя", description = "Сохраняет картинку пользователя",
            parameters = { @Parameter(name = "file", description = "Файл картинки") })
    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadImageToUser(@RequestParam("file") MultipartFile file, Principal principal) {
        imageUploadService.uploadImageToUser(file, principal);
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }

    @Operation(
            operationId = "Сохранить картинку поста", description = "Сохраняет картинку поста",
            parameters = { @Parameter(name = "postId", description = "Идентификатор поста"),
                    @Parameter(name = "file", description = "Файл картинки") })
    @PostMapping("/{postId}/upload")
    public ResponseEntity<MessageResponse> uploadImageToPost(@PathVariable("postId") String postId,
            @RequestParam("file") MultipartFile file, Principal principal) throws IOException {
        imageUploadService.uploadImageToPost(file, principal, Long.parseLong(postId));
        return ResponseEntity.ok(new MessageResponse("Image Uploaded Successfully"));
    }

    @Operation(
            operationId = "Получить картинку пользователя", description = "Получает картинку пользователя")
    @GetMapping("/profileImage")
    public ResponseEntity<ImageModel> getImageToUser(Principal principal) {
        ImageModel userImage = imageUploadService.getImageToUser(principal);
        return new ResponseEntity<>(userImage, HttpStatus.OK);
    }

    @Operation(
            operationId = "Получить картинку поста", description = "Получает картинку поста",
            parameters = { @Parameter(name = "postId", description = "Идентификатор поста") })
    @GetMapping("/{postId}/image")
    public ResponseEntity<ImageModel> getImageToPost(@PathVariable("postId") String postId) {
        ImageModel postImage = imageUploadService.getImageToPost(Long.parseLong(postId));
        return new ResponseEntity<>(postImage, HttpStatus.OK);
    }

}
