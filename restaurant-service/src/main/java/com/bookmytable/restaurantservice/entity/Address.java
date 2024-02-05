package com.bookmytable.restaurantservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ADDRESS")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@SequenceGenerator(name = "address_gen", sequenceName = "address_gen", initialValue = 1)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long restaurantId;
    private String state;
    private String city;
    private String area;
    private String addressLine1;
    private String addressLine2;
//    @OneToOne(cascade = {CascadeType.ALL},
//            fetch = FetchType.LAZY)
//    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
//    private Restaurant restaurant;
}
