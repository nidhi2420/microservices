package com.hotel_service.service;

import java.util.List;

import com.hotel_service.entities.Hotel;

public interface HotelService {
	//create

    Hotel create(Hotel hotel);

    //get all
    List<Hotel> getAll();

    //get single
    Hotel get(Long id);
}
