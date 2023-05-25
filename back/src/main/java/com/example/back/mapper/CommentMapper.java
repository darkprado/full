package com.example.back.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.back.dto.CommentDto;
import com.example.back.entity.Comment;
import com.example.back.entity.Post;
import com.example.back.entity.User;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "id", source = "commentDTO.id")
    @Mapping(target = "message", source = "commentDTO.message")
    @Mapping(target = "username", source = "commentDTO.username")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "post", source = "post")
    Comment fromDto(CommentDto commentDTO, Post post, User user);

    CommentDto toDto(Comment comment);

}
