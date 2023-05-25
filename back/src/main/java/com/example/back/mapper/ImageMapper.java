package com.example.back.mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

import org.mapstruct.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.example.back.entity.ImageModel;
import com.example.back.entity.Post;
import com.example.back.entity.User;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper {

    Logger LOG = LoggerFactory.getLogger(ImageMapper.class);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "imageBytes", ignore = true)
    @Mapping(target = "userId", source = "user.id")
    ImageModel toUserImage(MultipartFile file, User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "imageBytes", ignore = true)
    @Mapping(target = "postId", source = "post.id")
    ImageModel toPostImage(MultipartFile file, Post post);

    @AfterMapping
    default void afterMapping(@MappingTarget ImageModel imageModel, MultipartFile file) {
        try {
            imageModel.setImageBytes(compressBytes(file.getBytes()));
        } catch (IOException ex) {
            LOG.error("Cannot get bytes in file");
        }
        imageModel.setName(file.getOriginalFilename());
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            LOG.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

}
