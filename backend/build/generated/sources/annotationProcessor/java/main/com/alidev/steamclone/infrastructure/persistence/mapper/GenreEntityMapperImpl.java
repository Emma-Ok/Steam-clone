package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.infrastructure.persistence.entity.GenreEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T19:45:59-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GenreEntityMapperImpl implements GenreEntityMapper {

    @Override
    public Genre toDomain(GenreEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        String code = null;
        String name = null;

        id = entity.getId();
        code = entity.getCode();
        name = entity.getName();

        Genre genre = new Genre( id, code, name );

        return genre;
    }

    @Override
    public GenreEntity toEntity(Genre genre) {
        if ( genre == null ) {
            return null;
        }

        GenreEntity.GenreEntityBuilder genreEntity = GenreEntity.builder();

        genreEntity.id( genre.getId() );
        genreEntity.code( genre.getCode() );
        genreEntity.name( genre.getName() );

        return genreEntity.build();
    }
}
