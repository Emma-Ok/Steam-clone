package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.Platform;
import com.alidev.steamclone.domain.repository.PlatformRepository;
import com.alidev.steamclone.infrastructure.persistence.mapper.PlatformEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.PlatformJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PlatformPersistenceAdapter implements PlatformRepository {

    private final PlatformJpaRepository repository;
    private final PlatformEntityMapper mapper;

    public PlatformPersistenceAdapter(PlatformJpaRepository repository, PlatformEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Platform> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Platform> findByCode(String code) {
        return repository.findByCode(code).map(mapper::toDomain);
    }
}
