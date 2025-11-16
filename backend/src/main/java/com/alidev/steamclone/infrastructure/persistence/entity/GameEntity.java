package com.alidev.steamclone.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 5000)
    private String description;

    @Column(nullable = false, name = "base_price_amount")
    private BigDecimal basePriceAmount;

    @Column(nullable = false, name = "base_price_currency")
    private String basePriceCurrency;

    @Column(nullable = false, name = "current_price_amount")
    private BigDecimal currentPriceAmount;

    @Column(nullable = false, name = "current_price_currency")
    private String currentPriceCurrency;

    @Column(nullable = false)
    private Instant releaseDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "game_genres",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Builder.Default
    private Set<GenreEntity> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "game_platforms",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    @Builder.Default
    private Set<PlatformEntity> platforms = new HashSet<>();
}
