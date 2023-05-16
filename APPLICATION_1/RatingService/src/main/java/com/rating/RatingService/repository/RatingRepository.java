package com.rating.RatingService.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rating.RatingService.entities.Rating;

@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

	// custom finder methods
	List<Rating> findByUserId(String userId);
	
	List<Rating> findByHotelId(String hotelId);
	
}
