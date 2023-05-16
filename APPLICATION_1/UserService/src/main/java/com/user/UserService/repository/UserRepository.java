package com.user.UserService.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.UserService.entities.User;

public interface UserRepository extends JpaRepository<User, String>{

}
