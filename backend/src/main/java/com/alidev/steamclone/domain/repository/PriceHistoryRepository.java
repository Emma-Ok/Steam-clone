package com.alidev.steamclone.domain.repository;

import com.alidev.steamclone.domain.model.PriceHistory;

import java.util.List;
import java.util.UUID;

public interface PriceHistoryRepository {
    PriceHistory save(PriceHistory priceHistory);
    List<PriceHistory> findByGameId(UUID gameId);
}
