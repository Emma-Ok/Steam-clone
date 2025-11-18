package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.catalog.GenreResponse;
import com.alidev.steamclone.application.dto.catalog.PlatformResponse;
import com.alidev.steamclone.application.dto.catalog.RoleResponse;
import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.domain.model.Platform;
import com.alidev.steamclone.domain.model.Role;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-16T21:30:19-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class CatalogMapperImpl implements CatalogMapper {

    @Override
    public GenreResponse toGenreResponse(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        UUID id = null;
        String code = null;
        String name = null;

        id = genre.getId();
        code = genre.getCode();
        name = genre.getName();

        GenreResponse genreResponse = new GenreResponse( id, code, name );

        return genreResponse;
    }

    @Override
    public PlatformResponse toPlatformResponse(Platform platform) {
        if ( platform == null ) {
            return null;
        }

        UUID id = null;
        String code = null;
        String name = null;

        id = platform.getId();
        code = platform.getCode();
        name = platform.getName();

        PlatformResponse platformResponse = new PlatformResponse( id, code, name );

        return platformResponse;
    }

    @Override
    public RoleResponse toRoleResponse(Role role) {
        if ( role == null ) {
            return null;
        }

        UUID id = null;
        String name = null;

        id = role.getId();
        name = role.getName();

        RoleResponse roleResponse = new RoleResponse( id, name );

        return roleResponse;
    }
}
