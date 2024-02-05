package com.bookmytable.restaurantservice.repository;

import com.bookmytable.restaurantservice.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByRestaurantId(Long restaurantId);
    List<Address> findByStateContainingAndCityContainingAndAreaContaining(String state, String city, String area);
    List<Address> findByStateContainingAndCityContaining(String state, String city);

    List<Address> findByRestaurantIdIn(List<Long> restaurantIdList);
}
