package com.userservice.UserService.externalServices;


import com.userservice.UserService.entities.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@FeignClient(name="HOTEL-SERVICE")
public interface HotelService
{
    @GetMapping("/hotels/{hotelID}")
    Hotel getHotel(@PathVariable String hotelID);

    //
    @GetMapping("/hotels")
    List<Hotel> getAllHotel();

}
