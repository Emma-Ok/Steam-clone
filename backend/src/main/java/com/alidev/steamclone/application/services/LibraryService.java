package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.library.LibraryEntryRequest;
import com.alidev.steamclone.application.dto.library.LibraryEntryResponse;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.LibraryEntry;
import com.alidev.steamclone.domain.repository.GameRepository;
import com.alidev.steamclone.domain.repository.LibraryRepository;
import com.alidev.steamclone.infrastructure.mapper.LibraryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final GameRepository gameRepository;
    private final LibraryMapper libraryMapper;

    public LibraryService(LibraryRepository libraryRepository,
                          GameRepository gameRepository,
                          LibraryMapper libraryMapper) {
        this.libraryRepository = libraryRepository;
        this.gameRepository = gameRepository;
        this.libraryMapper = libraryMapper;
    }

    @Transactional
    public LibraryEntryResponse addOrUpdate(UUID userId, LibraryEntryRequest request) {
        gameRepository.findById(request.gameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        LibraryEntry entry = libraryRepository.findByUserIdAndGameId(userId, request.gameId())
            .orElseGet(() -> new LibraryEntry(null, userId, request.gameId(), EnumSet.noneOf(LibraryEntry.Status.class), 0, null));

        Set<LibraryEntry.Status> statuses = request.statuses() == null || request.statuses().isEmpty()
            ? EnumSet.of(LibraryEntry.Status.OWNED)
            : EnumSet.copyOf(request.statuses());

        entry.replaceStatuses(statuses);
        if (request.progressMinutes() != null) {
            entry.updateProgress(request.progressMinutes());
        }
        LibraryEntry saved = libraryRepository.save(entry);
        return libraryMapper.toResponse(saved);
    }

    @Transactional
    public void remove(UUID userId, UUID gameId) {
        libraryRepository.deleteByUserIdAndGameId(userId, gameId);
    }
}
