package com.alidev.steamclone.application.dto.user;

import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record UpdateUserRequest(@NotBlank String username, Set<String> favoriteGenres) {}
