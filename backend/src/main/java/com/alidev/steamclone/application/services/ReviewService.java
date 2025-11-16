package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.review.CreateReviewRequest;
import com.alidev.steamclone.application.dto.review.ReviewResponse;
import com.alidev.steamclone.application.dto.review.UpdateReviewRequest;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.Review;
import com.alidev.steamclone.domain.repository.GameRepository;
import com.alidev.steamclone.domain.repository.ReviewRepository;
import com.alidev.steamclone.domain.valueobjects.Rating;
import com.alidev.steamclone.infrastructure.mapper.ReviewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final GameRepository gameRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository,
                         GameRepository gameRepository,
                         ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.gameRepository = gameRepository;
        this.reviewMapper = reviewMapper;
    }

    @Transactional
    public ReviewResponse create(UUID userId, CreateReviewRequest request) {
        gameRepository.findById(request.gameId())
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        Review review = new Review(null, request.gameId(), userId, Rating.of(request.rating()), request.comment(), null, null);
        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Transactional
    public ReviewResponse update(UUID reviewId, UpdateReviewRequest request) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        review.update(Rating.of(request.rating()), request.comment());
        Review saved = reviewRepository.save(review);
        return reviewMapper.toResponse(saved);
    }

    @Transactional
    public void delete(UUID reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> listByGame(UUID gameId) {
        gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        return reviewRepository.findByGameId(gameId).stream().map(reviewMapper::toResponse).toList();
    }
}
