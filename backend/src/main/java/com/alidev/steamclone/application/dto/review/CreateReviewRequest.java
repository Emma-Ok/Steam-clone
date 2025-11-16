package com.alidev.steamclone.application.dto.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateReviewRequest(@NotNull UUID gameId,
                                  @Min(1) @Max(5) int rating,
                                  @NotBlank String comment) {}
