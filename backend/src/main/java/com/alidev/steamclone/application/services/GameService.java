package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.game.GameResponse;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.repository.GameRepository;
import com.alidev.steamclone.infrastructure.mapper.GameMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public GameService(GameRepository gameRepository, GameMapper gameMapper) {
        this.gameRepository = gameRepository;
        this.gameMapper = gameMapper;
    }

    @Transactional(readOnly = true)
    public List<GameResponse> list() {
        return gameRepository.findAll().stream().map(gameMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public GameResponse get(UUID id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        return gameMapper.toResponse(game);
    }
}
