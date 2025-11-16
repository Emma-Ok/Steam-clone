package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.Platform;

import java.util.List;
import java.util.Optional;

public interface PlatformRepository {
    List<Platform> findAll();
    Optional<Platform> findByCode(String code);
}
