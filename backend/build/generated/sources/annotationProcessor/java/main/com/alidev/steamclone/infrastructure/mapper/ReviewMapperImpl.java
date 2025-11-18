package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.review.ReviewResponse;
import com.alidev.steamclone.domain.model.Review;
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
public class ReviewMapperImpl implements ReviewMapper {

    @Override
    public ReviewResponse toResponse(Review review) {
        if ( review == null ) {
            return null;
        }

        UUID id = null;
        UUID userId = null;
        UUID gameId = null;
        int rating = 0;
        String comment = null;
        Instant createdAt = null;
        Instant updatedAt = null;

        id = review.getId();
        userId = review.getUserId();
        gameId = review.getGameId();
        rating = map( review.getRating() );
        comment = review.getComment();
        createdAt = review.getCreatedAt();
        updatedAt = review.getUpdatedAt();

        ReviewResponse reviewResponse = new ReviewResponse( id, userId, gameId, rating, comment, createdAt, updatedAt );

        return reviewResponse;
    }
}
