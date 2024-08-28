package com.userservice.UserService.controller;


import com.userservice.UserService.entities.User;
import com.userservice.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUSer(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }



    @GetMapping("/{userId}")
     @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        User user1 = userService.getUser(userId);
        return ResponseEntity.ok(user1);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<User> updateUserIfPresent(@RequestBody Map<String, Object> updates, @PathVariable String userId) {
        User updatedUser = userService.updateUserIfPresent(userId, updates);

         return ResponseEntity.ok(updatedUser);
    }


    @GetMapping()
    @CircuitBreaker(name = "ratingHotelGetAllBreaker", fallbackMethod = "ratingHotelGetAllFallback")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = userService.getAllUser();

        return ResponseEntity.ok(userList);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable String userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }


    //Creating Fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        log.info("Fallback is executed because service is down", ex.getMessage());
        User user = User.builder()
                .email("dummy_email@gmail.com")
                .name("Dummy_Name")
                .userId("1234_Dummy_userId")
                .about("This Dummy user is created because of Some Services are down")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    public ResponseEntity<List<User>> ratingHotelGetAllFallback(Exception ex) {
        log.info("Fallback is executed because service is down", ex.getMessage());
        User user1 = User.builder()
                .email("dummy1@gmail.com")
                .name("Dummy 1")
                .userId("1234")
                .about("This user is created because of Some Services are down")
                .build();
        User user2 = User.builder()
                .email("dummy2@gmail.com")
                .name("Dummy 2")
                .userId("5678")
                .about("This user is created because of Some Services are down")
                .build();
        List<User>userList=new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);

    }
}
