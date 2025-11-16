package com.alidev.steamclone.application.dto.library;

import com.alidev.steamclone.domain.model.LibraryEntry;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record LibraryEntryResponse(UUID id, UUID userId, UUID gameId, Set<LibraryEntry.Status> statuses,
                                   int progressMinutes, Instant addedAt) {}
