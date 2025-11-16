package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.game.GameResponse;
import com.alidev.steamclone.application.dto.pricing.MoneyDto;
import com.alidev.steamclone.domain.model.Game;
import com.alidev.steamclone.domain.valueobjects.Money;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "basePrice", expression = "java(toMoneyDto(game.getBasePrice()))")
    @Mapping(target = "currentPrice", expression = "java(toMoneyDto(game.getCurrentPrice()))")
    @Mapping(target = "genres", expression = "java(game.getGenres().stream().map(g -> g.getCode()).collect(java.util.stream.Collectors.toSet()))")
    @Mapping(target = "platforms", expression = "java(game.getPlatforms().stream().map(p -> p.getCode()).collect(java.util.stream.Collectors.toSet()))")
    GameResponse toResponse(Game game);

    default MoneyDto toMoneyDto(Money money) {
        if (money == null) {
            return null;
        }
        return new MoneyDto(money.amount(), money.currency().getCurrencyCode());
    }
}
