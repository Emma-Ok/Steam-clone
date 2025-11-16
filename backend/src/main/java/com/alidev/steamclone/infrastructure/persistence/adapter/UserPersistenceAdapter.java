package com.alidev.steamclone.infrastructure.persistence.adapter;

import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.UserRepository;
import com.alidev.steamclone.domain.valueobjects.Email;
import com.alidev.steamclone.infrastructure.persistence.entity.UserEntity;
import com.alidev.steamclone.infrastructure.persistence.mapper.UserEntityMapper;
import com.alidev.steamclone.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserPersistenceAdapter implements UserRepository {

    private final UserJpaRepository repository;
    private final UserEntityMapper mapper;

    public UserPersistenceAdapter(UserJpaRepository repository, UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return repository.findByEmail(email.value()).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(user.getCreatedAt());
        }
        return mapper.toDomain(repository.save(entity));
    }
}
