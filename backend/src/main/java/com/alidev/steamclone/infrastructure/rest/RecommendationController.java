package com.alidev.steamclone.infrastructure.rest;

import com.alidev.steamclone.application.dto.recommendation.RecommendationResponse;
import com.alidev.steamclone.application.services.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RecommendationResponse> get(@PathVariable UUID userId) {
        return ResponseEntity.ok(recommendationService.recommendForUser(userId));
    }
}
