package com.user.UserService.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.UserService.entities.Hotel;
import com.user.UserService.entities.Rating;
import com.user.UserService.entities.User;
import com.user.UserService.exceptions.ResourceNotFoundException;
import com.user.UserService.external.services.HotelService;
import com.user.UserService.repository.UserRepository;
import com.user.UserService.service.UsersService;

@Service
public class UserServiceImpl implements UsersService {

	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;
    
    private Logger logger = LoggerFactory.getLogger(UsersService.class);
	
	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(String userId) {
		User user =  userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server :: " + userId));
	    // Call Rating service using REST TEMPLATE
	    Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(), Rating[].class);
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		
		List<Rating> hotelRatings = ratings.stream().map(rating -> {
		//	ResponseEntity<Hotel> hotel = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
		    Hotel hotel = hotelService.getHotel(rating.getHotelId());
//		rating.setHotel(hotel.getBody());
		    rating.setHotel(hotel);
		    return rating;
		}).collect(Collectors.toList());
		user.setRatings(hotelRatings);
		return user;
	}

}
