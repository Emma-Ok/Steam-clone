package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Platform;
import com.alidev.steamclone.infrastructure.persistence.entity.PlatformEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlatformEntityMapper {
    Platform toDomain(PlatformEntity entity);
    PlatformEntity toEntity(Platform platform);
}
