package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.LibraryEntry;
import com.alidev.steamclone.infrastructure.persistence.entity.LibraryEntryEntity;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-16T21:30:19-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class LibraryEntryEntityMapperImpl implements LibraryEntryEntityMapper {

    @Override
    public LibraryEntry toDomain(LibraryEntryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Set<LibraryEntry.Status> statuses = null;
        UUID id = null;
        UUID userId = null;
        UUID gameId = null;
        int progressMinutes = 0;
        Instant addedAt = null;

        Set<LibraryEntry.Status> set = entity.getStatuses();
        if ( set != null ) {
            statuses = new LinkedHashSet<LibraryEntry.Status>( set );
        }
        id = entity.getId();
        userId = entity.getUserId();
        gameId = entity.getGameId();
        progressMinutes = entity.getProgressMinutes();
        addedAt = entity.getAddedAt();

        LibraryEntry libraryEntry = new LibraryEntry( id, userId, gameId, statuses, progressMinutes, addedAt );

        return libraryEntry;
    }

    @Override
    public LibraryEntryEntity toEntity(LibraryEntry entry) {
        if ( entry == null ) {
            return null;
        }

        LibraryEntryEntity.LibraryEntryEntityBuilder libraryEntryEntity = LibraryEntryEntity.builder();

        libraryEntryEntity.id( entry.getId() );
        libraryEntryEntity.userId( entry.getUserId() );
        libraryEntryEntity.gameId( entry.getGameId() );
        Set<LibraryEntry.Status> set = entry.getStatuses();
        if ( set != null ) {
            libraryEntryEntity.statuses( new LinkedHashSet<LibraryEntry.Status>( set ) );
        }
        libraryEntryEntity.progressMinutes( entry.getProgressMinutes() );
        libraryEntryEntity.addedAt( entry.getAddedAt() );

        return libraryEntryEntity.build();
    }
}
