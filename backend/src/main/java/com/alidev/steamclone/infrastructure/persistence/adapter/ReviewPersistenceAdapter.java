package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.Review;
import com.alidev.steamclone.domain.repository.ReviewRepository;
import com.alidev.steamclone.infrastructure.persistence.entity.ReviewEntity;
import com.alidev.steamclone.infrastructure.persistence.mapper.ReviewEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.ReviewJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ReviewPersistenceAdapter implements ReviewRepository {

    private final ReviewJpaRepository repository;
    private final ReviewEntityMapper mapper;

    public ReviewPersistenceAdapter(ReviewJpaRepository repository, ReviewEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Review save(Review review) {
        ReviewEntity entity = mapper.toEntity(review);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
            entity.setCreatedAt(Instant.now());
        }
        entity.setUpdatedAt(Instant.now());
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Review> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Review> findByGameId(UUID gameId) {
        return repository.findByGameId(gameId).stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
