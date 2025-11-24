package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.PriceHistory;
import com.alidev.steamclone.domain.valueobjects.Money;
import com.alidev.steamclone.infrastructure.persistence.entity.PriceHistoryEntity;
import java.time.Instant;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-23T19:46:00-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class PriceHistoryEntityMapperImpl implements PriceHistoryEntityMapper {

    @Override
    public PriceHistory toDomain(PriceHistoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        UUID gameId = null;
        Instant changedAt = null;

        id = entity.getId();
        gameId = entity.getGameId();
        changedAt = entity.getChangedAt();

        Money price = toMoney(entity.getAmount(), entity.getCurrency());

        PriceHistory priceHistory = new PriceHistory( id, gameId, price, changedAt );

        return priceHistory;
    }

    @Override
    public PriceHistoryEntity toEntity(PriceHistory history) {
        if ( history == null ) {
            return null;
        }

        PriceHistoryEntity.PriceHistoryEntityBuilder priceHistoryEntity = PriceHistoryEntity.builder();

        priceHistoryEntity.id( history.getId() );
        priceHistoryEntity.gameId( history.getGameId() );
        priceHistoryEntity.changedAt( history.getChangedAt() );

        priceHistoryEntity.amount( history.getPrice().amount() );
        priceHistoryEntity.currency( history.getPrice().currency().getCurrencyCode() );

        return priceHistoryEntity.build();
    }
}
