package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    Role toDomain(RoleEntity entity);
    RoleEntity toEntity(Role role);
}
