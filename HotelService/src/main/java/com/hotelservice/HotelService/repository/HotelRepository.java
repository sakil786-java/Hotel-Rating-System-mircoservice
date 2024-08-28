package com.hotelservice.HotelService.repository;

import com.hotelservice.HotelService.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HotelRepository extends JpaRepository<Hotel,String > {

}
