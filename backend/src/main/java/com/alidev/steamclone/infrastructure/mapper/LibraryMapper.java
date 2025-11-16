package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.library.LibraryEntryResponse;
import com.alidev.steamclone.domain.model.LibraryEntry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LibraryMapper {
    LibraryEntryResponse toResponse(LibraryEntry entry);
}
