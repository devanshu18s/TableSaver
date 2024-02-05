package com.bookmytable.restaurantservice.controller;

import com.bookmytable.restaurantservice.entity.AverageRating;
import com.bookmytable.restaurantservice.entity.Restaurant;
import com.bookmytable.restaurantservice.service.FetchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    FetchDataService fetchDataService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllRestaurants() {
        List<Restaurant> restaurantList = fetchDataService.findAll();
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getRestaurantById(@PathVariable("id") Long id) {
        Restaurant restaurant = fetchDataService.findById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @GetMapping(value = "/getByName")
    public ResponseEntity<?> getRestaurantsListByName(@RequestParam("restaurantName") String restaurantName) {
        List<Restaurant> restaurantList = fetchDataService.findByName(restaurantName);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/getByArea")
    public ResponseEntity<?> getRestaurantsListByCityAndArea(@RequestParam("state") String state,
                                                             @RequestParam("city") String city,
                                                             @RequestParam("area") String area) {
        List<Restaurant> restaurantList = fetchDataService.getRestaurantsListByCityAndArea(state, city, area);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @GetMapping(value = "/getByCity")
    public ResponseEntity<?> getRestaurantsListByCity(@RequestParam("state") String state,
                                                             @RequestParam("city") String city) {
        List<Restaurant> restaurantList = fetchDataService.getRestaurantsListByCity(state, city);
        return new ResponseEntity<>(restaurantList, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant newRestaurant = fetchDataService.createRestaurant(restaurant);
        return new ResponseEntity<>(newRestaurant, HttpStatus.CREATED);
    }

    @GetMapping(value = "/getAverageRating/{id}")
    public ResponseEntity<?> getAverageRating(@PathVariable("id") long restaurantId) {
        AverageRating avgRating = fetchDataService.getAverageRating(restaurantId);
        return new ResponseEntity<>(avgRating, HttpStatus.OK);
    }

}
