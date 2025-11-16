package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.domain.repository.GenreRepository;
import com.alidev.steamclone.infrastructure.persistence.mapper.GenreEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.GenreJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenrePersistenceAdapter implements GenreRepository {

    private final GenreJpaRepository repository;
    private final GenreEntityMapper mapper;

    public GenrePersistenceAdapter(GenreJpaRepository repository, GenreEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Genre> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Genre> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDomain);
    }
}
