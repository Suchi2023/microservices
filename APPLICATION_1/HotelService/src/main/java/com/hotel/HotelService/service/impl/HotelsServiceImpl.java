package com.hotel.HotelService.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hotel.HotelService.entities.Hotel;
import com.hotel.HotelService.exceptions.ResourceNotFoundException;
import com.hotel.HotelService.repository.HotelRepository;
import com.hotel.HotelService.service.HotelsService;

@Service
public class HotelsServiceImpl implements HotelsService{

	@Autowired
	private HotelRepository hotelRepository;
	
	@Override
	public Hotel saveHotel(Hotel hotel) {
		String randomId = UUID.randomUUID().toString();
		hotel.setHotelId(randomId);
		return hotelRepository.save(hotel);
	}

	@Override
	public List<Hotel> getAllHotels() {
		return hotelRepository.findAll();
	}

	@Override
	public Hotel getHotelById(String id) {
		return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel with given id is not found on server :: " + id));
	}

}
