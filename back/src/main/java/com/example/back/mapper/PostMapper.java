package com.example.back.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.example.back.dto.PostDto;
import com.example.back.entity.Post;
import com.example.back.entity.User;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "likedUsers", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "id", source = "postDto.id")
    @Mapping(target = "likes", source = "postDto", qualifiedByName = "likes")
    Post fromDto(PostDto postDto, User user);

    @Named("likes")
    default Integer mapLikedUsers(PostDto postDto) {
        return 0;
    }

    @Mapping(target = "username", source = "post.user.username")
    PostDto toDto(Post post);
}
