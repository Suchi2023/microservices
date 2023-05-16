package com.user.UserService.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.UserService.entities.User;
import com.user.UserService.service.UsersService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UsersService usersService;
	
	 private Logger logger = LoggerFactory.getLogger(UserController.class);
	 int retryCounter = 1;

	@PostMapping
	public ResponseEntity<User> saveUser(@RequestBody User user){
		User user1 = usersService.saveUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user1);
	}
	
	@GetMapping("/{id}")
	// @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
	// @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
	@RateLimiter(name = "userRateLimiter" , fallbackMethod = "ratingHotelFallback")
	public ResponseEntity<User> getSingleUser(@PathVariable("id") String userId){
		logger.info("Get Single User Handler : UserController");
		logger.info("Retry count : {} ", retryCounter);
		retryCounter++;
		User user = usersService.getUserById(userId);
		return ResponseEntity.ok(user);
	}

	// fallback method for circuit breaker
	public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
		logger.info("Fallback is executed because service is down : " + ex.getMessage());
		User user = User.builder()
				.userName("Dummy")
				.emailId("dummy@gmail.com")
				.about("This user is created dummy because few services are down")
				.userId("123456").build();
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(){
		List<User> users = usersService.getAllUsers();
		return ResponseEntity.ok(users);
	}
}
