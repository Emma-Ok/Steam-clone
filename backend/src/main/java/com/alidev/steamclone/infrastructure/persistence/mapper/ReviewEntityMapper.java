package com.alidev.steamclone.infrastructure.persistence.mapper;

import com.alidev.steamclone.domain.model.Review;
import com.alidev.steamclone.domain.valueobjects.Rating;
import com.alidev.steamclone.infrastructure.persistence.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewEntityMapper {

    @Mapping(target = "rating", expression = "java(Rating.of(entity.getRating()))")
    Review toDomain(ReviewEntity entity);

    @Mapping(target = "rating", expression = "java(review.getRating().value())")
    ReviewEntity toEntity(Review review);
}
