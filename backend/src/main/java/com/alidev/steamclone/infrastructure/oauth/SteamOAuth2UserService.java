package com.alidev.steamclone.infrastructure.oauth;

import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.repository.RoleRepository;
import com.alidev.steamclone.domain.repository.UserRepository;
import com.alidev.steamclone.domain.valueobjects.Email;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class SteamOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SteamOAuth2UserService(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User user = delegate.loadUser(request);
        String emailValue = resolveEmail(user.getAttributes());
        Email email = Email.of(emailValue);
        User domainUser = userRepository.findByEmail(email)
                .orElseGet(() -> provisionUser(user, email));

        Map<String, Object> attributes = new HashMap<>(user.getAttributes());
        attributes.put("steam_user_id", domainUser.getId().toString());
        attributes.put("username", domainUser.getUsername());
        var authorities = domainUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
        String nameAttribute = user.getName();
        return new DefaultOAuth2User(authorities, attributes, nameAttribute != null ? nameAttribute : "sub");
    }

    private User provisionUser(OAuth2User oauthUser, Email email) {
        Role defaultRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> new Role(UUID.randomUUID(), "ROLE_USER"));
        String username = oauthUser.getAttribute("name");
        if (username == null) {
            username = email.value().split("@")[0];
        }
        return userRepository.save(new User(null, email, username,
                passwordEncoder.encode(UUID.randomUUID().toString()), Set.of(defaultRole), null, null, Instant.now()));
    }

    private String resolveEmail(Map<String, Object> attributes) {
        Object email = attributes.get("email");
        if (email instanceof String value && !value.isBlank()) {
            return value;
        }
        Object login = attributes.get("login");
        if (login instanceof String value && !value.isBlank()) {
            return value + "@github.local";
        }
        throw new IllegalStateException("OAuth provider did not return an email");
    }
}
