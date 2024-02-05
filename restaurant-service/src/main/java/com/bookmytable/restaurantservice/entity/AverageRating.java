package com.bookmytable.restaurantservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AverageRating {
    private long id;
    private long restaurantId;
    private float averageStarsRating;
    private long totalUsers;
    private long totalComments;
    private Timestamp ratingsUpdateTimestamp;
}
