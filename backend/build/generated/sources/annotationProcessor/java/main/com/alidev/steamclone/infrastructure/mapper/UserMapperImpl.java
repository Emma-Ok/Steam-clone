package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.user.UserResponse;
import com.alidev.steamclone.domain.model.User;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T19:45:59-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponse toResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UUID id = null;
        String username = null;
        Instant createdAt = null;

        id = user.getId();
        username = user.getUsername();
        createdAt = user.getCreatedAt();

        String email = user.getEmail().value();
        Set<String> roles = user.getRoles().stream().map(role -> role.getName()).collect(java.util.stream.Collectors.toSet());
        Set<String> favoriteGenres = user.getFavoriteGenres().stream().map(genre -> genre.getCode()).collect(java.util.stream.Collectors.toSet());

        UserResponse userResponse = new UserResponse( id, email, username, roles, favoriteGenres, createdAt );

        return userResponse;
    }
}
