package com.alidev.steamclone.application.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Email String email,
        @NotBlank String username,
        @Size(min = 8, max = 64) String password
) {}
