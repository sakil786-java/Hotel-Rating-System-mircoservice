package com.hotelservice.HotelService.controller;



import com.hotelservice.HotelService.entities.Hotel;
import com.hotelservice.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;


    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel)
    {
        Hotel hotel1 = hotelService.create(hotel);

        return  ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
    }


    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotel(@PathVariable String hotelId)
    {
        Hotel hotel = hotelService.getHotel(hotelId);

        return  ResponseEntity.ok(hotel);
    }


    @GetMapping()
    public ResponseEntity<List<Hotel>> getAllHotel()
    {
        List<Hotel> hotelList = hotelService.getAllHotels();

        return  ResponseEntity.ok(hotelList);
    }

    @DeleteMapping ("/{hotelId}")
    public ResponseEntity<String> deleteHotelById(@PathVariable String hotelId)
    {
        hotelService.deleteHotel(hotelId);

        return new ResponseEntity<>(hotelId, HttpStatus.OK);
    }

}
