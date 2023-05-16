package com.rating.RatingService.service;

import java.util.List;

import com.rating.RatingService.entities.Rating;

public interface RatingsService {

	// create a rating record
	Rating create(Rating rating);
	
	// get all the ratings
	List<Rating> getAllRatings();
	
	// get Ratings by user Id
	List<Rating> getRatingByUserId(String userId);
	
	// get Ratings by hotel Id
	List<Rating> getRatingByHotelId(String hotelId);
	
}
