package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.game.GameResponse;
import com.alidev.steamclone.application.dto.pricing.PriceUpdateRequest;
import com.alidev.steamclone.domain.exceptions.BusinessRuleException;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.model.PriceHistory;
import com.alidev.steamclone.domain.repository.GameRepository;
import com.alidev.steamclone.domain.repository.PriceHistoryRepository;
import com.alidev.steamclone.domain.valueobjects.Money;
import com.alidev.steamclone.infrastructure.mapper.GameMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

@Service
public class PricingService {

    private final GameRepository gameRepository;
    private final PriceHistoryRepository priceHistoryRepository;
    private final GameMapper gameMapper;

    public PricingService(GameRepository gameRepository,
                          PriceHistoryRepository priceHistoryRepository,
                          GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.priceHistoryRepository = priceHistoryRepository;
        this.gameMapper = gameMapper;
    }

    @Transactional
    public GameResponse registerPriceChange(PriceUpdateRequest request) {
        Game game = gameRepository.findById(request.gameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));

        Money newPrice = Money.of(request.amount(), request.currency());
        if (!game.getBasePrice().currency().equals(newPrice.currency())) {
            throw new BusinessRuleException("Currency mismatch");
        }
        game.updatePrice(newPrice);
        priceHistoryRepository.save(new PriceHistory(null, game.getId(), newPrice, null));
        Game saved = gameRepository.save(game);
        return gameMapper.toResponse(saved);
    }
}
