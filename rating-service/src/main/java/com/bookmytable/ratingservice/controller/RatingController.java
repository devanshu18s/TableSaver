package com.bookmytable.ratingservice.controller;

import com.bookmytable.ratingservice.entity.Rating;
import com.bookmytable.ratingservice.service.FetchRatingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RatingController {

    @Autowired
    FetchRatingService fetchRatingService;

    @PostMapping(value = "/ratings")
    public ResponseEntity<?> addRestaurantRating(@RequestBody Rating rating,
                                                 @RequestHeader("userId") long userId) {
        log.info("Request received for adding new rating by the user: {} for restaurant: {}",
                 userId, rating.getRestaurantId());
        Rating newRating = fetchRatingService.addRestaurantRating(rating, userId);
        return new ResponseEntity<>(newRating, HttpStatus.OK);
    }

    @GetMapping(value = "/ratings")
    public ResponseEntity<?> fetchAllRatings() {
        List<Rating> ratingList = fetchRatingService.fetchAllRatings();
        return new ResponseEntity<>(ratingList, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllRatingsForRestaurant")
    public List<Rating> getAllRatingsForRestaurant(@RequestParam("restaurantId") long restaurantId) {
        List<Rating> ratingsList = fetchRatingService.fetchRatingsForRestaurant(restaurantId);
        return ratingsList;
    }
}
