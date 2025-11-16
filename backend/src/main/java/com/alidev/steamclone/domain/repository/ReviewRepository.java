package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.Review;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository {
    Review save(Review review);
    Optional<Review> findById(UUID id);
    List<Review> findByGameId(UUID gameId);
    void deleteById(UUID id);
}
