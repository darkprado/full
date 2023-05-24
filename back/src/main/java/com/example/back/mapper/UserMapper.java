package com.example.back.mapper;

import java.util.Collections;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.back.dto.UserDto;
import com.example.back.entity.User;
import com.example.back.enums.ERole;
import com.example.back.payload.request.SignupRequest;

/**
 * @author s.melekhin
 * @since 24 май 2023 г.
 */
@Mapper(componentModel = "spring")
public abstract class UserMapper {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void setbCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Mapping(target = "password", source = "request", qualifiedByName = "mapPassword")
    @Mapping(target = "roles", source = "request", qualifiedByName = "mapRoles")
    public abstract User createFromRequest(SignupRequest request);

    @Mapping(target = "firstname", source = "userDto.firstname")
    @Mapping(target = "lastname", source = "userDto.lastname")
    @Mapping(target = "bio", source = "userDto.bio")
    @Mapping(target = "id", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "password", source = "user.password")
    @Mapping(target = "createdDate", source = "user.createdDate")
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "posts", ignore = true)
    public abstract User updateFromDto(UserDto userDto, User user);

    @Named("mapPassword")
    public String mapPassword(SignupRequest request) {
        return bCryptPasswordEncoder.encode(request.getPassword());
    }

    @Named("mapRoles")
    public Set<ERole> mapRoles(SignupRequest request) {
        return Collections.singleton(ERole.ROLE_USER);
    }

}
