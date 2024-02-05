package com.bookmytable.restaurantservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

    private long id;
    private long restaurantId;
    private int ratingStars;
    private String comment;
    private long userId;
    private Timestamp commentTime;

}
