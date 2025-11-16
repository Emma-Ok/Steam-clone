package com.alidev.steamclone.application.dto.pricing;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record PriceUpdateRequest(@NotNull UUID gameId,
                                 @DecimalMin("0.0") BigDecimal amount,
                                 @NotBlank String currency) {}
