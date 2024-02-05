package com.bookmytable.ratingservice.repository;

import com.bookmytable.ratingservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByUserIdAndRestaurantId(long userId, long restaurantId);

    List<Rating> findByRestaurantId(long restaurantId);

    List<Rating> findAllByRestaurantId(long restaurantId);
}
