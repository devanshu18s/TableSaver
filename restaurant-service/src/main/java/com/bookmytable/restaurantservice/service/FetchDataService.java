package com.bookmytable.restaurantservice.service;

import com.bookmytable.restaurantservice.client.RatingAccessClient;
import com.bookmytable.restaurantservice.entity.Address;
import com.bookmytable.restaurantservice.entity.AverageRating;
import com.bookmytable.restaurantservice.entity.Restaurant;
import com.bookmytable.restaurantservice.repository.AddressRepository;
import com.bookmytable.restaurantservice.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class FetchDataService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    RatingAccessClient ratingAccessClient;

    public List<Restaurant> findAll() {
        List<Restaurant> restaurantList = restaurantRepository.findAll();
        if (nonNull(restaurantList) && !restaurantList.isEmpty()) {
            setAddress(restaurantList);
            setAverageRating(restaurantList);
        }
//        setAverageRating(restaurantList);
        return restaurantList;
    }

    public Restaurant findById(Long id) {
        Optional<Restaurant> restuarantOptional = restaurantRepository.findById(id);
        if (restuarantOptional.isPresent()) {
            setAddress(Arrays.asList(restuarantOptional.get()));
        } else {
            throw new RuntimeException("Restaurant not found !!");
        }
        return restuarantOptional.get();
    }

    public List<Restaurant> findByName(String restaurantName) {
        List<Restaurant> restaurantList = restaurantRepository.findByNameContaining(restaurantName);
        if (nonNull(restaurantList) && !restaurantList.isEmpty()) {
            setAddress(restaurantList);
//        setAverageRating(restaurantList);
            return restaurantList;
        } else {
            throw new RuntimeException("Restaurant not found !!");
        }
    }

    public List<Restaurant> getRestaurantsListByCityAndArea(String state, String city, String area) {
        List<Address> addressList = addressRepository.findByStateContainingAndCityContainingAndAreaContaining(state,
                                                                                                              city,
                                                                                                              area);
        List<Restaurant> restaurantList = new LinkedList<>();
        addressList.stream().forEach(address -> {
            Optional<Restaurant> restaurant = restaurantRepository.findById(address.getRestaurantId());
            if (restaurant.isPresent()) {
                restaurant.get().setAddress(address);
                restaurantList.add(restaurant.get());
            }
        });
        if (nonNull(restaurantList) && !restaurantList.isEmpty()) {
            return restaurantList;
        } else {
            throw new RuntimeException("Restaurant not found !!");
        }
    }

    public List<Restaurant> getRestaurantsListByCity(String state, String city) {
        List<Address> addressList = addressRepository.findByStateContainingAndCityContaining(state, city);
        List<Restaurant> restaurantList = new LinkedList<>();
        addressList.stream().forEach(address -> {
            Optional<Restaurant> restaurant = restaurantRepository.findById(address.getRestaurantId());
            if (restaurant.isPresent()) {
                restaurant.get().setAddress(address);
                restaurantList.add(restaurant.get());
            }
        });
        if (nonNull(restaurantList) && !restaurantList.isEmpty()) {
            return restaurantList;
        } else {
            throw new RuntimeException("Restaurant not found !!");
        }
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        restaurant.setId(0L);
        Restaurant newRestaurant = restaurantRepository.save(restaurant);
        Address address = restaurant.getAddress();
        address.setId(0L);
        address.setRestaurantId(newRestaurant.getId());
        addressRepository.save(address);
        newRestaurant.setAddress(address);
        return newRestaurant;
    }

    private void setAverageRating(List<Restaurant> restaurantList) {
        List<AverageRating> averageRatingList = ratingAccessClient.getAverageRatingForRestaurant(
                restaurantList
                        .stream()
                        .map(restaurant -> String.valueOf(restaurant.getId()))
                        .reduce("", (a,b) -> a + "," + b));

        if(nonNull(averageRatingList) && !averageRatingList.isEmpty()) {
            restaurantList.stream().forEach(restaurant -> {
                List<AverageRating> avgRating =
                        averageRatingList.stream()
                                   .filter(rating -> rating.getRestaurantId() == restaurant.getId().longValue())
                                   .toList();
                if (nonNull(avgRating) && !avgRating.isEmpty()) {
                    restaurant.setAvgRating(avgRating.get(0));
                }
            });
        }
    }

    private void setAddress(List<Restaurant> restaurantList) {
        List<Address> addressList = addressRepository.findByRestaurantIdIn(restaurantList
                                                                                   .stream()
                                                                                   .map(restaurant -> restaurant.getId())
                                                                                   .toList());
        restaurantList.stream().forEach(restaurant -> {
            List<Address> addresses =
                    addressList.stream()
                               .filter(adr -> adr.getRestaurantId().longValue() == restaurant.getId().longValue())
                               .toList();
            if (nonNull(addresses) && !addresses.isEmpty()) {
                restaurant.setAddress(addresses.get(0));
            }
        });
    }

    public AverageRating getAverageRating(long restaurantId) {
        List<AverageRating> avgRatingList =
                ratingAccessClient.getAverageRatingForRestaurant(String.valueOf(restaurantId));
        if(nonNull(avgRatingList) && !avgRatingList.isEmpty()) {
            return avgRatingList.get(0);
        }
        return null;
    }
}
