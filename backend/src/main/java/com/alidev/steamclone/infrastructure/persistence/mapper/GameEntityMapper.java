package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.valueobjects.Money;
import com.alidev.steamclone.infrastructure.persistence.entity.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(componentModel = "spring", uses = {GenreEntityMapper.class, PlatformEntityMapper.class})
public interface GameEntityMapper {

    @Mapping(target = "basePrice", expression = "java(toMoney(entity.getBasePriceAmount(), entity.getBasePriceCurrency()))")
    @Mapping(target = "currentPrice", expression = "java(toMoney(entity.getCurrentPriceAmount(), entity.getCurrentPriceCurrency()))")
    Game toDomain(GameEntity entity);

    @Mapping(target = "basePriceAmount", expression = "java(game.getBasePrice().amount())")
    @Mapping(target = "basePriceCurrency", expression = "java(game.getBasePrice().currency().getCurrencyCode())")
    @Mapping(target = "currentPriceAmount", expression = "java(game.getCurrentPrice().amount())")
    @Mapping(target = "currentPriceCurrency", expression = "java(game.getCurrentPrice().currency().getCurrencyCode())")
    GameEntity toEntity(Game game);

    default Money toMoney(BigDecimal amount, String currency) {
        if (amount == null || currency == null) {
            return null;
        }
        return new Money(amount, Currency.getInstance(currency));
    }
}
