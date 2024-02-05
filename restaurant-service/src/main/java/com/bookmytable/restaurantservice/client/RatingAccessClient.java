package com.bookmytable.restaurantservice.client;

import com.bookmytable.restaurantservice.entity.AverageRating;
import com.bookmytable.restaurantservice.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${feign.rating.service.name}", url = "${feign.rating.service.url}")
public interface RatingAccessClient {

    @GetMapping(value = "/getAverageRating")
    List<AverageRating> getAverageRatingForRestaurant(@RequestParam String restaurantIdList);

    @GetMapping(value = "/getAllRatingsForRestaurant")
    List<Rating> getAllRatingsForRestaurant(@RequestParam("restaurantId") long restaurantId);
}
