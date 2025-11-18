package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.domain.model.Role;
import com.alidev.steamclone.domain.model.User;
import com.alidev.steamclone.domain.valueobjects.Email;
import com.alidev.steamclone.infrastructure.persistence.entity.GenreEntity;
import com.alidev.steamclone.infrastructure.persistence.entity.RoleEntity;
import com.alidev.steamclone.infrastructure.persistence.entity.UserEntity;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-16T21:30:19-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Autowired
    private RoleEntityMapper roleEntityMapper;
    @Autowired
    private GenreEntityMapper genreEntityMapper;

    @Override
    public User toDomain(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Set<Role> roles = null;
        Set<Genre> favoriteGenres = null;
        Set<String> favoritePlatformCodes = null;
        UUID id = null;
        String username = null;
        String passwordHash = null;
        Instant createdAt = null;

        roles = roleEntitySetToRoleSet( entity.getRoles() );
        favoriteGenres = genreEntitySetToGenreSet( entity.getFavoriteGenres() );
        Set<String> set2 = entity.getFavoritePlatformCodes();
        if ( set2 != null ) {
            favoritePlatformCodes = new LinkedHashSet<String>( set2 );
        }
        id = entity.getId();
        username = entity.getUsername();
        passwordHash = entity.getPasswordHash();
        createdAt = entity.getCreatedAt();

        Email email = Email.of(entity.getEmail());

        User user = new User( id, email, username, passwordHash, roles, favoriteGenres, favoritePlatformCodes, createdAt );

        return user;
    }

    @Override
    public UserEntity toEntity(User user) {
        if ( user == null ) {
            return null;
        }

        UserEntity.UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( user.getId() );
        userEntity.username( user.getUsername() );
        userEntity.passwordHash( user.getPasswordHash() );
        userEntity.roles( roleSetToRoleEntitySet( user.getRoles() ) );
        userEntity.favoriteGenres( genreSetToGenreEntitySet( user.getFavoriteGenres() ) );
        Set<String> set2 = user.getFavoritePlatformCodes();
        if ( set2 != null ) {
            userEntity.favoritePlatformCodes( new LinkedHashSet<String>( set2 ) );
        }
        userEntity.createdAt( user.getCreatedAt() );

        userEntity.email( user.getEmail().value() );

        return userEntity.build();
    }

    protected Set<Role> roleEntitySetToRoleSet(Set<RoleEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Role> set1 = new LinkedHashSet<Role>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( RoleEntity roleEntity : set ) {
            set1.add( roleEntityMapper.toDomain( roleEntity ) );
        }

        return set1;
    }

    protected Set<Genre> genreEntitySetToGenreSet(Set<GenreEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Genre> set1 = new LinkedHashSet<Genre>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( GenreEntity genreEntity : set ) {
            set1.add( genreEntityMapper.toDomain( genreEntity ) );
        }

        return set1;
    }

    protected Set<RoleEntity> roleSetToRoleEntitySet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleEntity> set1 = new LinkedHashSet<RoleEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleEntityMapper.toEntity( role ) );
        }

        return set1;
    }

    protected Set<GenreEntity> genreSetToGenreEntitySet(Set<Genre> set) {
        if ( set == null ) {
            return null;
        }

        Set<GenreEntity> set1 = new LinkedHashSet<GenreEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Genre genre : set ) {
            set1.add( genreEntityMapper.toEntity( genre ) );
        }

        return set1;
    }
}
