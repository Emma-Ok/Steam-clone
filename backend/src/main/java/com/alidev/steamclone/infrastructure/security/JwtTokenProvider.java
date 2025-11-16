package com.alidev.steamclone.infrastructure.security;

import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.TokenProviderPort;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements TokenProviderPort {

    private final JwtEncoder jwtEncoder;

    public JwtTokenProvider(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @Override
    public String generate(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("steam-clone")
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofHours(4)))
                .subject(user.getId().toString())
                .claim("username", user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
