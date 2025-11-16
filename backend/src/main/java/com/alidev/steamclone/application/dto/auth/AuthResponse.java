package com.alidev.steamclone.application.dto.auth;

import java.util.Set;
import java.util.UUID;

public record AuthResponse(UUID userId, String username, String email, Set<String> roles, String accessToken) {}
