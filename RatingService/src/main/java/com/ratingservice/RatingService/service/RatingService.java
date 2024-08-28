package com.ratingservice.RatingService.service;


import com.ratingservice.RatingService.entities.Rating;

import java.util.List;

public interface RatingService {


    //create
    Rating create(Rating rating);
    //get all ratings
    List<Rating> getAllRatings();
    //get all ratings by UserId
    List<Rating>getRatingsByUserId(String userId);
    //get All by Hotel
    List<Rating>getRatingByHotelId(String hotelId);
}
