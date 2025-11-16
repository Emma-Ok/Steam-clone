package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    List<Role> findAll();
}
