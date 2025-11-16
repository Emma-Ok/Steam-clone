package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.catalog.GenreResponse;
import com.alidev.steamclone.application.dto.catalog.PlatformResponse;
import com.alidev.steamclone.application.dto.catalog.RoleResponse;
import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.domain.model.Platform;
import com.alidev.steamclone.domain.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    GenreResponse toGenreResponse(Genre genre);
    PlatformResponse toPlatformResponse(Platform platform);
    RoleResponse toRoleResponse(Role role);
}
