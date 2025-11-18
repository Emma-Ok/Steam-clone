package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Review;
import com.alidev.steamclone.domain.valueobjects.Rating;
import com.alidev.steamclone.infrastructure.persistence.entity.ReviewEntity;
import java.time.Instant;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-16T21:30:19-0500",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-9.2.0.jar, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class ReviewEntityMapperImpl implements ReviewEntityMapper {

    @Override
    public Review toDomain(ReviewEntity entity) {
        if ( entity == null ) {
            return null;
        }

        UUID id = null;
        UUID gameId = null;
        UUID userId = null;
        String comment = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = entity.getId();
        gameId = entity.getGameId();
        userId = entity.getUserId();
        comment = entity.getComment();
        createdAt = entity.getCreatedAt();
        updatedAt = entity.getUpdatedAt();

        Rating rating = Rating.of(entity.getRating());

        Review review = new Review( id, gameId, userId, rating, comment, createdAt, updatedAt );

        return review;
    }

    @Override
    public ReviewEntity toEntity(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewEntity.ReviewEntityBuilder reviewEntity = ReviewEntity.builder();

        reviewEntity.id( review.getId() );
        reviewEntity.gameId( review.getGameId() );
        reviewEntity.userId( review.getUserId() );
        reviewEntity.comment( review.getComment() );
        reviewEntity.createdAt( review.getCreatedAt() );
        reviewEntity.updatedAt( review.getUpdatedAt() );

        reviewEntity.rating( review.getRating().value() );

        return reviewEntity.build();
    }
}
