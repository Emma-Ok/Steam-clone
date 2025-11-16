package com.alidev.steamclone.infrastructure.oauth;

import com.alidev.steamclone.domain.exceptions.ResourceNotFoundException;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.TokenProviderPort;
import com.alidev.steamclone.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final TokenProviderPort tokenProviderPort;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public OAuth2LoginSuccessHandler(UserRepository userRepository,
                                     TokenProviderPort tokenProviderPort) {
        this.userRepository = userRepository;
        this.tokenProviderPort = tokenProviderPort;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User principal = (OAuth2User) authentication.getPrincipal();
        String userId = principal.getAttribute("steam_user_id");
        if (userId == null) {
            throw new ServletException("Missing steam_user_id attribute in OAuth2 response");
        }
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new ResourceNotFoundException("OAuth2 user not found"));
        String token = tokenProviderPort.generate(user);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(Map.of("accessToken", token)));
    }
}
