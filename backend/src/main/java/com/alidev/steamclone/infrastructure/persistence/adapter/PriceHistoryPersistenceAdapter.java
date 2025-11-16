package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.PriceHistory;
import com.alidev.steamclone.domain.repository.PriceHistoryRepository;
import com.alidev.steamclone.infrastructure.persistence.entity.PriceHistoryEntity;
import com.alidev.steamclone.infrastructure.persistence.mapper.PriceHistoryEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.PriceHistoryJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
public class PriceHistoryPersistenceAdapter implements PriceHistoryRepository {

    private final PriceHistoryJpaRepository repository;
    private final PriceHistoryEntityMapper mapper;

    public PriceHistoryPersistenceAdapter(PriceHistoryJpaRepository repository,
                                          PriceHistoryEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public PriceHistory save(PriceHistory priceHistory) {
        PriceHistoryEntity entity = mapper.toEntity(priceHistory);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getChangedAt() == null) {
            entity.setChangedAt(Instant.now());
        }
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public List<PriceHistory> findByGameId(UUID gameId) {
        return repository.findByGameId(gameId).stream().map(mapper::toDomain).toList();
    }
}
