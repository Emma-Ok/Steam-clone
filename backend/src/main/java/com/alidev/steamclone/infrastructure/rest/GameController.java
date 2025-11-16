package com.alidev.steamclone.infrastructure.rest;

import com.alidev.steamclone.application.dto.game.GameResponse;
import com.alidev.steamclone.application.dto.review.ReviewResponse;
import com.alidev.steamclone.application.services.GameService;
import com.alidev.steamclone.application.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final ReviewService reviewService;

    public GameController(GameService gameService, ReviewService reviewService) {
        this.gameService = gameService;
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<GameResponse>> list() {
        return ResponseEntity.ok(gameService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameResponse> get(@PathVariable UUID id) {
        return ResponseEntity.ok(gameService.get(id));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponse>> reviews(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.listByGame(id));
    }
}
