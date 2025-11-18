package com.alidev.steamclone.application.services;

import com.alidev.steamclone.application.dto.auth.AuthResponse;
import com.alidev.steamclone.application.dto.auth.LoginRequest;
import com.alidev.steamclone.application.dto.auth.RegisterRequest;
import com.alidev.steamclone.application.usecases.LoginUserUseCase;
import com.alidev.steamclone.application.usecases.RegisterUserUseCase;
import com.alidev.steamclone.domain.exceptions.BusinessRuleException;
import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.RoleRepository;
import com.alidev.steamclone.domain.repository.TokenProviderPort;
import com.alidev.steamclone.domain.repository.UserRepository;
import com.alidev.steamclone.domain.valueobjects.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class AuthService implements RegisterUserUseCase, LoginUserUseCase {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProviderPort tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       TokenProviderPort tokenProvider,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        var email = Email.of(request.email());
        userRepository.findByEmail(email).ifPresent(u -> {
            throw new BusinessRuleException("Email already registered");
        });

        Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

        User user = new User(null, email, request.username(), passwordEncoder.encode(request.password()),
                Set.of(userRole), null, null, null);
        User saved = userRepository.save(user);
        return toAuthResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        var email = Email.of(request.email());
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new BusinessRuleException("Invalid credentials");
        }
        return toAuthResponse(user);
    }

    private AuthResponse toAuthResponse(User user) {
        String token = tokenProvider.generate(user);
        Set<String> roles = user.getRoles().stream().map(Role::getName).collect(java.util.stream.Collectors.toSet());
        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail().value(), roles, token);
    }
}
