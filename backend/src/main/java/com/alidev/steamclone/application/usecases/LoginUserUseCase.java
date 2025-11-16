package com.alidev.steamclone.application.usecases;

import com.alidev.steamclone.application.dto.auth.AuthResponse;
import com.alidev.steamclone.application.dto.auth.LoginRequest;

public interface LoginUserUseCase {
    AuthResponse login(LoginRequest request);
}
