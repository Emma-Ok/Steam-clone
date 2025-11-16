package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.model.Recommendation;
import com.alidev.steamclone.domain.repository.RecommendationRepository;
import com.alidev.steamclone.infrastructure.persistence.entity.RecommendationEntity;
import com.alidev.steamclone.infrastructure.persistence.mapper.GameEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.GameJpaRepository;
import com.alidev.steamclone.infrastructure.persistence.repository.RecommendationJpaRepository;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationPersistenceAdapter implements RecommendationRepository {

    private final RecommendationJpaRepository repository;
    private final GameJpaRepository gameJpaRepository;
    private final GameEntityMapper gameEntityMapper;

    public RecommendationPersistenceAdapter(RecommendationJpaRepository repository,
                                            GameJpaRepository gameJpaRepository,
                                            GameEntityMapper gameEntityMapper) {
        this.repository = repository;
        this.gameJpaRepository = gameJpaRepository;
        this.gameEntityMapper = gameEntityMapper;
    }

    @Override
    public Optional<Recommendation> findLatestByUserId(UUID userId) {
        return repository.findTopByUserIdOrderByGeneratedAtDesc(userId)
                .map(entity -> new Recommendation(userId, loadGames(entity.getGameIds())));
    }

    @Override
    public Recommendation save(Recommendation recommendation) {
        RecommendationEntity entity = RecommendationEntity.builder()
                .id(UUID.randomUUID())
                .userId(recommendation.userId())
                .gameIds(recommendation.gamesSuggested().stream().map(Game::getId).toList())
                .generatedAt(Instant.now())
                .build();
        RecommendationEntity saved = repository.save(entity);
        return new Recommendation(saved.getUserId(), loadGames(saved.getGameIds()));
    }

    private List<Game> loadGames(List<UUID> ids) {
        return gameJpaRepository.findAllById(ids).stream().map(gameEntityMapper::toDomain).toList();
    }
}
