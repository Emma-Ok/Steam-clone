package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.valueobjects.Email;
import com.alidev.steamclone.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleEntityMapper.class, GenreEntityMapper.class})
public interface UserEntityMapper {

    @Mapping(target = "email", expression = "java(Email.of(entity.getEmail()))")
    User toDomain(UserEntity entity);

    @Mapping(target = "email", expression = "java(user.getEmail().value())")
    UserEntity toEntity(User user);
}
