package com.hotel_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_management.dto.LoginRequest;
import com.hotel_management.dto.Response;
import com.hotel_management.entity.User;
import com.hotel_management.service.interfaces.UserService;

@RestController
@RequestMapping(path = "/htms/auth")
public class AuthController {

	@Autowired
	private UserService userService; 
	
	@PostMapping(path = "/register")
	public ResponseEntity<Response> register(@RequestBody User user){
		Response response = userService.register(user);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
		Response response = userService.login(loginRequest);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
}
