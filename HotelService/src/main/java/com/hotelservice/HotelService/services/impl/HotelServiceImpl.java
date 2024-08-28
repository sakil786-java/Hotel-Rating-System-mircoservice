package com.hotelservice.HotelService.services.impl;


import com.hotelservice.HotelService.entities.Hotel;
import com.hotelservice.HotelService.exception.ResourceNotFoundException;
import com.hotelservice.HotelService.repository.HotelRepository;
import com.hotelservice.HotelService.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);

        return hotelRepository.save(hotel);
    }

    @Override
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @Override
    public Hotel getHotel(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel With given Id Not Found"));
    }

    @Override
    public String deleteHotel(String id) {
        Hotel findHotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel With Given ID is not found!!! " + id));
        if (findHotel != null) {
            hotelRepository.deleteById(id);
        }
        return findHotel.getId();
    }
}
