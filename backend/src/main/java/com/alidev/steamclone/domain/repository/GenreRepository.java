package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();
    Optional<Genre> findByCode(String code);
}
