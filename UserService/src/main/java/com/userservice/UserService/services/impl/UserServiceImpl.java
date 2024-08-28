package com.userservice.UserService.services.impl;


import com.userservice.UserService.entities.Hotel;
import com.userservice.UserService.entities.Rating;
import com.userservice.UserService.entities.User;
import com.userservice.UserService.exception.ResourceNotFoundException;
import com.userservice.UserService.externalServices.HotelService;
import com.userservice.UserService.externalServices.RatingService;
import com.userservice.UserService.repository.UserRepository;
import com.userservice.UserService.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.client.RestTemplate;


import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

    @Override
    public User saveUSer(User user) {
        //Unique User Id
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }


    @Override
    public List<User> getAllUser() {
        List<User> userList = userRepository.findAll();
        List<User> newUserList = new ArrayList<>();
        List<Hotel> allHotel = hotelService.getAllHotel();

        for (User u : userList) {
            String userid = u.getUserId();
            List<Rating> ratingList = ratingService.getRatingsByUserId(userid);
            // Initialize an empty list to hold all ratings for the user
            List<Rating> newRatingList = new ArrayList<>();

            for (Rating r : ratingList) {
                String hotelId = r.getHotelId();
                Hotel hotel = hotelService.getHotel(hotelId);
                r.setHotel(hotel);
                // Add each rating to the user's rating list
                newRatingList.add(r);
            }
            // Set the full list of ratings to the user
            u.setRatings(newRatingList);
            // Add the user with all their ratings to the new user list
            newUserList.add(u);
        }

        return newUserList;
    }

    @Override
    public User getUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User With Given ID is not found!!! " + userId));

        Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        List<Rating> ratingList = ratings
                .stream()
                .map(rating -> {
                    //api call to hotel service to get the hotel
                    //http://localhost:8082/hotels/{hotelId}
                    // ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
                    // Hotel hotel = forEntity.getBody();

                    //Same Process using feign Client by using HotelService
                    Hotel hotel = hotelService.getHotel(rating.getHotelId());

                    //set the hotel to rating
                    rating.setHotel(hotel);
                    //return the rating
                    return rating;

                }).collect(Collectors.toList());
        user.setRatings(ratingList);
        return user;
    }

    public User updateUserIfPresent(String userId, Map<String, Object> updates) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User With Given ID is not found!!! " + userId));

        updates.forEach((key, value) -> {
            // Skip updating the 'ratings' ,userId field
            if ("ratings".equals(key) || "userId".equals(key)) {
                return;
            }
            Field field = ReflectionUtils.findField(User.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, user, value);
            } else {
                throw new ResourceNotFoundException("No field with name " + key + " found on User class");
            }
        });

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @Override
    public String deleteUser(String userId) {
        User findUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User With Given ID is not found!!! " + userId));
        if (findUser != null) {
            userRepository.deleteById(userId);
        }
        return findUser.getUserId() + " Deleted Successfully";
    }
}
