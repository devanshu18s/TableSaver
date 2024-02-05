package com.bookmytable.ratingservice.controller;

import com.bookmytable.ratingservice.entity.AverageRating;
import com.bookmytable.ratingservice.service.FetchRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AverageRatingController {

    @Autowired
    FetchRatingService fetchRatingService;

    @GetMapping(value = "/getAverageRating")
    List<AverageRating> getAverageRatingForRestaurant(@RequestParam String restaurantIdList) {
        return fetchRatingService.getAverageRatingForRestaurant(restaurantIdList);
    }
}
