package com.ratingservice.RatingService.controller;



import com.ratingservice.RatingService.entities.Rating;
import com.ratingservice.RatingService.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;


    //Create rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating)
    {
        Rating rating1 = ratingService.create(rating);

        return  ResponseEntity.status(HttpStatus.CREATED).body(rating1);
    }
    @GetMapping()
    public ResponseEntity<List<Rating>> getAllRating()
    {
        List<Rating> ratingList = ratingService.getAllRatings();

        return  ResponseEntity.ok(ratingList);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable String userId)
    {
        List<Rating> ratingByUSerIDList = ratingService.getRatingsByUserId(userId);

        return  ResponseEntity.ok(ratingByUSerIDList);
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable String hotelId)
    {
        List<Rating> ratingByHotelIDList = ratingService.getRatingByHotelId(hotelId);

        return  ResponseEntity.ok(ratingByHotelIDList);
    }


}
