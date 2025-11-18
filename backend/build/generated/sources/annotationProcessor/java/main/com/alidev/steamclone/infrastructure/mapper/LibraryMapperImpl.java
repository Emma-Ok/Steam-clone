package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.library.LibraryEntryResponse;
import com.alidev.steamclone.domain.model.LibraryEntry;
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
public class LibraryMapperImpl implements LibraryMapper {

    @Override
    public LibraryEntryResponse toResponse(LibraryEntry entry) {
        if ( entry == null ) {
            return null;
        }

        UUID id = null;
        UUID userId = null;
        UUID gameId = null;
        Set<LibraryEntry.Status> statuses = null;
        int progressMinutes = 0;
        Instant addedAt = null;

        id = entry.getId();
        userId = entry.getUserId();
        gameId = entry.getGameId();
        Set<LibraryEntry.Status> set = entry.getStatuses();
        if ( set != null ) {
            statuses = new LinkedHashSet<LibraryEntry.Status>( set );
        }
        progressMinutes = entry.getProgressMinutes();
        addedAt = entry.getAddedAt();

        LibraryEntryResponse libraryEntryResponse = new LibraryEntryResponse( id, userId, gameId, statuses, progressMinutes, addedAt );

        return libraryEntryResponse;
    }
}
