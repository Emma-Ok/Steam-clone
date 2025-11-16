package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.repository.GameRepository;
import com.alidev.steamclone.infrastructure.persistence.entity.GameEntity;
import com.alidev.steamclone.infrastructure.persistence.mapper.GameEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.GameJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GamePersistenceAdapter implements GameRepository {

    private final GameJpaRepository repository;
    private final GameEntityMapper mapper;

    public GamePersistenceAdapter(GameJpaRepository repository, GameEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Game> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Game> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Game> findByGenreCodes(List<String> genreCodes) {
        return repository.findDistinctByGenres_CodeIn(genreCodes).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Game save(Game game) {
        GameEntity entity = mapper.toEntity(game);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        return mapper.toDomain(repository.save(entity));
    }
}
