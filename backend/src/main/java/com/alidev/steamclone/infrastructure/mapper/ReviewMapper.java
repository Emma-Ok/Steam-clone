package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.review.ReviewResponse;
import com.alidev.steamclone.domain.model.Review;
import com.alidev.steamclone.domain.valueobjects.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewResponse toResponse(Review review);

    default int map(Rating rating) {
        return rating == null ? 0 : rating.value();
    }
}
