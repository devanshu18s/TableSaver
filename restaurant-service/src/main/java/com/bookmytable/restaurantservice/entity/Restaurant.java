package com.bookmytable.restaurantservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@SequenceGenerator(name = "restaurant_gen", sequenceName = "restaurant_gen",  initialValue = 1000)
@Table(name = "RESTAURANT")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "restaurant_gen")
    private Long id;
    @Column(name = "Restaurant_Name")
    private String name;
    @Column(name = "Opening_Closing_Hrs")
    private String openHrs;
    @Transient
    private Address address;
    @Transient
    private AverageRating avgRating;
}
