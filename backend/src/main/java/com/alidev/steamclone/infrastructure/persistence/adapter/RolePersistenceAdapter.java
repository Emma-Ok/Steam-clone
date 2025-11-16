package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.domain.repository.RoleRepository;
import com.alidev.steamclone.infrastructure.persistence.mapper.RoleEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.RoleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RolePersistenceAdapter implements RoleRepository {

    private final RoleJpaRepository repository;
    private final RoleEntityMapper mapper;

    public RolePersistenceAdapter(RoleJpaRepository repository, RoleEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return repository.findByName(name).map(mapper::toDomain);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
