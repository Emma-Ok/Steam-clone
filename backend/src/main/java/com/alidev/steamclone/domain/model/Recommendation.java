package com.alidev.steamclone.domain.model;

import java.util.List;
import java.util.UUID;

public record Recommendation(UUID userId, List<Game> gamesSuggested) {
}
