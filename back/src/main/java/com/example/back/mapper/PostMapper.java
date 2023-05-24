package com.example.back.mapper;

import java.util.Set;

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
    @Mapping(target = "likes", source = "postDto", qualifiedByName = "likes")
    @Mapping(target = "id", source = "postDto.id")
    Post toPostCreate(PostDto postDto, User user);

    @Named("likes")
    default Integer mapLikedUsers(PostDto postDto) {
        return 0;
    }

}
