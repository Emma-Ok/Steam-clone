package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Platform;
import com.alidev.steamclone.infrastructure.persistence.entity.PlatformEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-16T21:30:19-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PlatformEntityMapperImpl implements PlatformEntityMapper {

    @Override
    public Platform toDomain(PlatformEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String code = null;
        String name = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();

        Platform platform = new Platform( id, code, name );

        return platform;
    }

    @Override
    public PlatformEntity toEntity(Platform platform) {
        if ( platform == null ) {
            return null;
        }

        PlatformEntity.PlatformEntityBuilder platformEntity = PlatformEntity.builder();

        platformEntity.id( platform.getId() );
        platformEntity.code( platform.getCode() );
        platformEntity.name( platform.getName() );

        return platformEntity.build();
    }
}
