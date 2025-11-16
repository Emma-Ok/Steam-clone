package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository {
    List<Game> findAll();
    Optional<Game> findById(UUID id);
    List<Game> findByGenreCodes(List<String> genreCodes);
    Game save(Game game);
}
