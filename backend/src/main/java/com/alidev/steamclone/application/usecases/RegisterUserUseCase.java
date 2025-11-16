package com.alidev.steamclone.application.usecases;

import com.alidev.steamclone.application.dto.auth.AuthResponse;
import com.alidev.steamclone.application.dto.auth.RegisterRequest;

public interface RegisterUserUseCase {
    AuthResponse register(RegisterRequest request);
}
