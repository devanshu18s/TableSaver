package com.bookmytable.ratingservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AverageRating {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long restaurantId;
    private float averageStarsRating;
    private long totalUsers;
    private long totalComments;
    private Timestamp ratingsUpdateTimestamp;

}
