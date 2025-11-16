package com.alidev.steamclone.infrastructure.mapper;

import com.alidev.steamclone.application.dto.review.ReviewResponse;
import com.alidev.steamclone.domain.model.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewResponse toResponse(Review review);
}
