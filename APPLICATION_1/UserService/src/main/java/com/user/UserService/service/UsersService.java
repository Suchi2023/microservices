package com.user.UserService.service;

import java.util.List;

import com.user.UserService.entities.User;

public interface UsersService {
	
	// create user
	User saveUser(User user);
	
	// get the user list
	List<User> getAllUsers();
	
	// get single user record by id
	User getUserById(String userId);


}
