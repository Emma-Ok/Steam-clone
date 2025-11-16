package com.alidev.steamclone.application.dto.pricing;

import java.math.BigDecimal;

public record MoneyDto(BigDecimal amount, String currency) {}
