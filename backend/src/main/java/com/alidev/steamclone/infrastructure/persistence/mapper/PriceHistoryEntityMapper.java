package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.PriceHistory;
import com.alidev.steamclone.domain.valueobjects.Money;
import com.alidev.steamclone.infrastructure.persistence.entity.PriceHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Currency;

@Mapper(componentModel = "spring")
public interface PriceHistoryEntityMapper {

    @Mapping(target = "price", expression = "java(toMoney(entity.getAmount(), entity.getCurrency()))")
    PriceHistory toDomain(PriceHistoryEntity entity);

    @Mapping(target = "amount", expression = "java(history.getPrice().amount())")
    @Mapping(target = "currency", expression = "java(history.getPrice().currency().getCurrencyCode())")
    PriceHistoryEntity toEntity(PriceHistory history);

    default Money toMoney(BigDecimal amount, String currency) {
        if (amount == null || currency == null) {
            return null;
        }
        return new Money(amount, Currency.getInstance(currency));
    }
}
