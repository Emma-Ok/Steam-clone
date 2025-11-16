package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.valueobjects.Email;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(Email email);
    User save(User user);
}
