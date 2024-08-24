package com.hotel_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_management.dto.Response;
import com.hotel_management.service.interfaces.UserService;

@RestController
@RequestMapping(path = "/htms/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> allUsers(){
		Response response = userService.getAllUsers();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping(path = "/get-by-id/{userId}")
	public ResponseEntity<Response> getUserById(@PathVariable("userId") String userId){
		Response response = userService.getUserById(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@DeleteMapping(path = "/delete/{userId}")
	public ResponseEntity<Response> deleteUser(@PathVariable("userId") String userId){
		Response response = userService.deleteUser(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping(path = "/get-logged-in-user-info")
	public ResponseEntity<Response> getMyInfo(){
		
		Authentication authencation = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authencation.getName();
		
		Response response = userService.getMyInfo(email);
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@GetMapping(path = "/get-booking-history/{userId}")
	public ResponseEntity<Response> getBookingHistory(@PathVariable("userId") String userId){
		Response response = userService.getUserBookingHistory(userId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	
}
