package com.bookmytable.ratingservice.repository;

import com.bookmytable.ratingservice.entity.AverageRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AverageRatingRepository extends JpaRepository<AverageRating, Long> {
    List<AverageRating> findByRestaurantIdIn(List<Long> restaurantIdList);

    AverageRating findByRestaurantId(long restaurantId);
}
