package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.model.Genre;
import com.alidev.steamclone.domain.model.Platform;
import com.alidev.steamclone.domain.valueobjects.Money;
import com.alidev.steamclone.infrastructure.persistence.entity.GameEntity;
import com.alidev.steamclone.infrastructure.persistence.entity.GenreEntity;
import com.alidev.steamclone.infrastructure.persistence.entity.PlatformEntity;
import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T19:45:59-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GameEntityMapperImpl implements GameEntityMapper {

    @Autowired
    private GenreEntityMapper genreEntityMapper;
    @Autowired
    private PlatformEntityMapper platformEntityMapper;

    @Override
    public Game toDomain(GameEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Instant releaseDate = null;
        Set<Genre> genres = null;
        Set<Platform> platforms = null;
        UUID id = null;
        String title = null;
        String description = null;

        releaseDate = entity.getReleaseDate();
        genres = genreEntitySetToGenreSet( entity.getGenres() );
        platforms = platformEntitySetToPlatformSet( entity.getPlatforms() );
        id = entity.getId();
        title = entity.getTitle();
        description = entity.getDescription();

        Money basePrice = toMoney(entity.getBasePriceAmount(), entity.getBasePriceCurrency());
        Money currentPrice = toMoney(entity.getCurrentPriceAmount(), entity.getCurrentPriceCurrency());

        Game game = new Game( id, title, description, genres, platforms, basePrice, currentPrice, releaseDate );

        return game;
    }

    @Override
    public GameEntity toEntity(Game game) {
        if ( game == null ) {
            return null;
        }

        GameEntity.GameEntityBuilder gameEntity = GameEntity.builder();

        gameEntity.id( game.getId() );
        gameEntity.title( game.getTitle() );
        gameEntity.description( game.getDescription() );
        gameEntity.releaseDate( game.getReleaseDate() );
        gameEntity.genres( genreSetToGenreEntitySet( game.getGenres() ) );
        gameEntity.platforms( platformSetToPlatformEntitySet( game.getPlatforms() ) );

        gameEntity.basePriceAmount( game.getBasePrice().amount() );
        gameEntity.basePriceCurrency( game.getBasePrice().currency().getCurrencyCode() );
        gameEntity.currentPriceAmount( game.getCurrentPrice().amount() );
        gameEntity.currentPriceCurrency( game.getCurrentPrice().currency().getCurrencyCode() );

        return gameEntity.build();
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

    protected Set<Platform> platformEntitySetToPlatformSet(Set<PlatformEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<Platform> set1 = new LinkedHashSet<Platform>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PlatformEntity platformEntity : set ) {
            set1.add( platformEntityMapper.toDomain( platformEntity ) );
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

    protected Set<PlatformEntity> platformSetToPlatformEntitySet(Set<Platform> set) {
        if ( set == null ) {
            return null;
        }

        Set<PlatformEntity> set1 = new LinkedHashSet<PlatformEntity>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Platform platform : set ) {
            set1.add( platformEntityMapper.toEntity( platform ) );
        }

        return set1;
    }
}
