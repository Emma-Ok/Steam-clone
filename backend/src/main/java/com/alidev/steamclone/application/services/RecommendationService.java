package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.game.GameResponse;
import com.alidev.steamclone.application.dto.recommendation.RecommendationResponse;
import com.alidev.steamclone.application.usecases.RecommendationUseCase;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.model.Recommendation;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.GameRepository;
import com.alidev.steamclone.domain.repository.RecommendationRepository;
import com.alidev.steamclone.domain.repository.UserRepository;
import com.alidev.steamclone.infrastructure.mapper.GameMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationService implements RecommendationUseCase {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final RecommendationRepository recommendationRepository;
    private final GameMapper gameMapper;

    public RecommendationService(UserRepository userRepository,
                                 GameRepository gameRepository,
                                 RecommendationRepository recommendationRepository,
                                 GameMapper gameMapper) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.recommendationRepository = recommendationRepository;
        this.gameMapper = gameMapper;
    }

    @Override
    @Transactional
    public RecommendationResponse recommendForUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<String> favoriteGenreCodes = user.getFavoriteGenres().stream()
                .map(genre -> genre.getCode())
                .toList();

        List<Game> candidates = favoriteGenreCodes.isEmpty()
                ? gameRepository.findAll()
                : gameRepository.findByGenreCodes(favoriteGenreCodes);

        List<Game> ranked = candidates.stream()
                .sorted(Comparator.comparing(Game::getReleaseDate).reversed())
                .distinct()
                .limit(10)
                .toList();

        Recommendation recommendation = new Recommendation(userId, ranked);
        recommendationRepository.save(recommendation);

        List<GameResponse> responses = ranked.stream().map(gameMapper::toResponse).toList();
        return new RecommendationResponse(userId, responses);
    }
}
