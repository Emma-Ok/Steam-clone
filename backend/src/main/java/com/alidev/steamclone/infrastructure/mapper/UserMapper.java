package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.user.UserResponse;
import com.alidev.steamclone.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "email", expression = "java(user.getEmail().value())")
    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName()).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "favoriteGenres", expression = "java(user.getFavoriteGenres().stream().map(genre -> genre.getCode()).collect(java.util.stream.Collectors.toSet()))")
    UserResponse toResponse(User user);
}
