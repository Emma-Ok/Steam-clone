package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.infrastructure.persistence.entity.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreEntityMapper {
    Genre toDomain(GenreEntity entity);
    GenreEntity toEntity(Genre genre);
}
