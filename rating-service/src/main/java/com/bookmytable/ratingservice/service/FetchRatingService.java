package com.bookmytable.ratingservice.service;

import com.bookmytable.ratingservice.entity.AverageRating;
import com.bookmytable.ratingservice.entity.Rating;
import com.bookmytable.ratingservice.repository.AverageRatingRepository;
import com.bookmytable.ratingservice.repository.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@Slf4j
public class FetchRatingService {

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    AverageRatingRepository averageRatingRepository;


    public List<AverageRating> getAverageRatingForRestaurant(String restaurantIdList) {
        List<AverageRating> averageRatingList = new ArrayList<>();
        if (nonNull(restaurantIdList) && !restaurantIdList.isEmpty()) {
            List<Long> idList = Arrays.stream(restaurantIdList.split(",")).map(Long::parseLong).toList();
            averageRatingList = averageRatingRepository.findByRestaurantIdIn(idList);
        }
        return averageRatingList;
    }

    public Rating addRestaurantRating(Rating rating, long userId) {
        log.info("Inside addRestaurantRating method with parameters -> rating: {}, userId: {}", rating, userId);
        if (nonNull(rating) && nonNull(userId)) {
            Rating checkRating = ratingRepository.findByUserIdAndRestaurantId(userId,
                                                                              rating.getRestaurantId());
            Timestamp timestamp = Timestamp.from(Instant.now());
            rating.setCommentTime(timestamp);
            if (isNull(checkRating)) {
                log.info("Adding new Rating by the userId: {} for the restaurantId: {}", userId,
                         rating.getRestaurantId());
                rating.setId(0L);
                rating.setUserId(userId);
            } else {
                log.info("Rating already given by the user: {} for the restaurantId: {}", userId,
                         rating.getRestaurantId());
                log.info("Updating the new rating");
            }
            Rating newRating = ratingRepository.save(rating);
            log.info("Updating the Average_Rating table");
            updateAverageRatingTable(newRating);
            return newRating;
        } else {
            throw new RuntimeException("Please provide a valid rating for the restaurant");
        }
    }

    private void updateAverageRatingTable(Rating newRating) {
        AverageRating checkAverageRating = averageRatingRepository.findByRestaurantId(newRating.getRestaurantId());
        if (isNull(checkAverageRating)) {
            log.info("No entry found in Average_Rating_Table for the restaurant: {]", newRating.getRestaurantId());
            log.info("Creating new record in Average_Rating_Table");
            List<Rating> ratingList = ratingRepository.findByRestaurantId(newRating.getRestaurantId());
            AverageRating averageRating = null;
            if (nonNull(ratingList) && !ratingList.isEmpty()) {
                float averageStars = (float) (ratingList.stream().map(e -> e.getRatingStars())
                                                        .reduce(0,
                                                                (o1, o2) -> o1 + o2) + newRating.getRatingStars())
                                     / (ratingList.size());
                long totalComments =
                        ratingList.stream().filter(e -> nonNull(e.getComment()) && !e.getComment().isEmpty())
                                  .map(Rating::getComment).count() + (nonNull(newRating.getComment()) ? 1 : 0);
                long totalUsers = ratingList.size();
                averageRating = AverageRating.builder()
                                             .id(0L)
                                             .restaurantId(newRating.getRestaurantId())
                                             .averageStarsRating(averageStars)
                                             .totalComments(totalComments)
                                             .totalUsers(totalUsers)
                                             .ratingsUpdateTimestamp(newRating.getCommentTime())
                                             .build();
                averageRatingRepository.save(averageRating);
            }
        } else {
            log.info("Entry found in the Average_Rating_Table. Updating the record");
            float updatedAverageRating =
                    (checkAverageRating.getAverageStarsRating() * checkAverageRating.getTotalUsers()
                     + newRating.getRatingStars()) / (checkAverageRating.getTotalUsers() + 1);
            checkAverageRating.setAverageStarsRating(updatedAverageRating);
            checkAverageRating.setTotalUsers(checkAverageRating.getTotalUsers() + 1);
            checkAverageRating.setTotalComments(nonNull(newRating.getComment()) ?
                                                checkAverageRating.getTotalComments() + 1
                                                                                :
                                                checkAverageRating.getTotalComments());
            checkAverageRating.setRatingsUpdateTimestamp(newRating.getCommentTime());
            averageRatingRepository.save(checkAverageRating);
        }
    }
    public List<Rating> fetchAllRatings() {
        return ratingRepository.findAll();
    }

    public List<Rating> fetchRatingsForRestaurant(long restaurantId) {
        String str = "abc";
        int a ;
        return ratingRepository.findAllByRestaurantId(restaurantId);
    }
}
