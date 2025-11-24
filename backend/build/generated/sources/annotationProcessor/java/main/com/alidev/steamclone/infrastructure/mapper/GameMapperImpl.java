package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.game.GameResponse;
import com.alidev.steamclone.application.dto.pricing.MoneyDto;
import com.alidev.steamclone.domain.model.Game;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T19:46:00-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public GameResponse toResponse(Game game) {
        if ( game == null ) {
            return null;
        }

        UUID id = null;
        String title = null;
        String description = null;
        Instant releaseDate = null;

        id = game.getId();
        title = game.getTitle();
        description = game.getDescription();
        releaseDate = game.getReleaseDate();

        MoneyDto basePrice = toMoneyDto(game.getBasePrice());
        MoneyDto currentPrice = toMoneyDto(game.getCurrentPrice());
        Set<String> genres = game.getGenres().stream().map(g -> g.getCode()).collect(java.util.stream.Collectors.toSet());
        Set<String> platforms = game.getPlatforms().stream().map(p -> p.getCode()).collect(java.util.stream.Collectors.toSet());

        GameResponse gameResponse = new GameResponse( id, title, description, basePrice, currentPrice, releaseDate, genres, platforms );

        return gameResponse;
    }
}
