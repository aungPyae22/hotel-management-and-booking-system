package com.hotel_management.service.interfaces;

import com.hotel_management.dto.LoginRequest;
import com.hotel_management.dto.Response;
import com.hotel_management.entity.User;

public interface UserService {
	
	Response register(User user);
	
	Response login(LoginRequest request);
	
	Response getAllUsers();
	
	Response getUserBookingHistory(String id);
	
	Response deleteUser(String id);
	
	Response getUserById(String id);
	
	Response getMyInfo(String email);
}
