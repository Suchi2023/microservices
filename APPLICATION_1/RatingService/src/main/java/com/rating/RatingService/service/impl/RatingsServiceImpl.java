package com.rating.RatingService.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rating.RatingService.entities.Rating;
import com.rating.RatingService.repository.RatingRepository;
import com.rating.RatingService.service.RatingsService;

@Service
public class RatingsServiceImpl implements RatingsService{

	@Autowired
	RatingRepository ratingRepository;
	
	@Override
	public Rating create(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		return ratingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		return ratingRepository.findByHotelId(hotelId);
	}

}
