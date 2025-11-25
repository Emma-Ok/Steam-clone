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
        String provider = null;
        if (request.getRequestURI().contains("/login/oauth2/code/")) {
            String[] parts = request.getRequestURI().split("/login/oauth2/code/");
            if (parts.length > 1) {
                provider = parts[1];
            }
        }

        String userId = null;
        if (provider != null) {
            switch (provider) {
                case "google":
                    // Google usa 'sub' como id único
                    userId = principal.getAttribute("sub");
                    break;
                case "github":
                    // GitHub usa 'id' como id único
                    userId = String.valueOf(principal.getAttribute("id"));
                    break;
                case "steam":
                    userId = principal.getAttribute("steam_user_id");
                    break;
                default:
                    // fallback: intenta con steam_user_id
                    userId = principal.getAttribute("steam_user_id");
            }
        }
        if (userId == null) {
            throw new ServletException("Missing user id attribute in OAuth2 response for provider: " + provider);
        }
        // Buscar usuario por el campo adecuado (ajusta según tu modelo de User)
        User user = null;
        if (provider != null && provider.equals("github")) {
            // GitHub id es numérico, pero tu User usa UUID, así que busca por email
            String email = principal.getAttribute("email");
            if (email != null) {
                user = userRepository.findByEmail(com.alidev.steamclone.domain.valueobjects.Email.of(email)).orElse(null);
            }
        } else if (provider != null && provider.equals("google")) {
            String email = principal.getAttribute("email");
            if (email != null) {
                user = userRepository.findByEmail(com.alidev.steamclone.domain.valueobjects.Email.of(email)).orElse(null);
            }
        } else {
            // Steam o fallback: busca por UUID
            try {
                user = userRepository.findById(UUID.fromString(userId)).orElse(null);
            } catch (Exception ignored) {}
        }
        if (user == null) {
            throw new ResourceNotFoundException("OAuth2 user not found");
        }
        String token = tokenProviderPort.generate(user);
        // Redirigir al frontend con el token como query param
        String redirectUrl = "http://localhost:3000/oauth-callback?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
