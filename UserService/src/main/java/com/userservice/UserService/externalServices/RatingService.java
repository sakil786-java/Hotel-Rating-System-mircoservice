package com.userservice.UserService.externalServices;



import com.userservice.UserService.entities.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(name="RATING-SERVICE")
public interface RatingService {

    @GetMapping("/ratings/users/{userId}")
    List<Rating> getRatingsByUserId(@PathVariable String userId);

}
