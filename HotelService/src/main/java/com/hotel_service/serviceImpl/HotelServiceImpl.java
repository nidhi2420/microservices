package com.hotel_service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel_service.entities.Hotel;
import com.hotel_service.exceptions.ResourceNotFoundException;
import com.hotel_service.repositories.HotelRepository;
import com.hotel_service.service.HotelService;
@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepository;
	@Override
	public Hotel create(Hotel hotel) {
		
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		
		return hotelRepository.findAll();
	}

	@Override
	public Hotel get(Long id) {
		
		return hotelRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("hotel not found with id : "+id));
	}

}
