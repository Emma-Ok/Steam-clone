package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.LibraryEntry;
import com.alidev.steamclone.domain.repository.LibraryRepository;
import com.alidev.steamclone.infrastructure.persistence.entity.LibraryEntryEntity;
import com.alidev.steamclone.infrastructure.persistence.mapper.LibraryEntryEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.LibraryEntryJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LibraryPersistenceAdapter implements LibraryRepository {

    private final LibraryEntryJpaRepository repository;
    private final LibraryEntryEntityMapper mapper;

    public LibraryPersistenceAdapter(LibraryEntryJpaRepository repository,
                                     LibraryEntryEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public LibraryEntry save(LibraryEntry entry) {
        LibraryEntryEntity entity = mapper.toEntity(entry);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            entity.setAddedAt(Instant.now());
        }
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void deleteByUserIdAndGameId(UUID userId, UUID gameId) {
        repository.deleteByUserIdAndGameId(userId, gameId);
    }

    @Override
    public Optional<LibraryEntry> findByUserIdAndGameId(UUID userId, UUID gameId) {
        return repository.findByUserIdAndGameId(userId, gameId).map(mapper::toDomain);
    }

    @Override
    public List<LibraryEntry> findByUserId(UUID userId) {
        return repository.findByUserId(userId).stream().map(mapper::toDomain).toList();
    }
}
