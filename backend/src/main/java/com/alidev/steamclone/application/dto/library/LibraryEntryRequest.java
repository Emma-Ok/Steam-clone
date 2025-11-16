package com.alidev.steamclone.application.dto.library;

import com.alidev.steamclone.domain.model.LibraryEntry;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record LibraryEntryRequest(@NotNull UUID gameId, Set<LibraryEntry.Status> statuses, Integer progressMinutes) {}
