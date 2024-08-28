package com.ratingservice.RatingService.repository;

import com.ratingservice.RatingService.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating,String> {

    //Custom Method
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
