package com.hotel.HotelService.service;

import java.util.List;

import com.hotel.HotelService.entities.Hotel;

public interface HotelsService {

	// create hotel
	public Hotel saveHotel(Hotel hotel);
	
	// get all hotels list
	public List<Hotel> getAllHotels();
	
	// get single hotel by id
	public Hotel getHotelById(String id);
	
}
