package com.hotel_management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotel_management.dto.LoginRequest;
import com.hotel_management.dto.Response;
import com.hotel_management.dto.UserDTO;
import com.hotel_management.entity.User;
import com.hotel_management.exception.OurException;
import com.hotel_management.repo.UserRepository;
import com.hotel_management.service.interfaces.UserService;
import com.hotel_management.utils.JWTUtils;
import com.hotel_management.utils.Utils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Override
	public Response register(User user) {
		Response response = new Response();
		try {
			
			if(user.getRole() == null || user.getRole().isBlank()) {
				user.setRole("USER");
			}
			
			if(repo.existsByEmail(user.getEmail())) {
				throw new OurException(user.getEmail() + " is Already exists.");
			}
			
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User saveUser = repo.save(user);
			UserDTO userDTO = Utils.mapUserEntityToUserDTO(saveUser);
			response.setStatusCode(200);
			response.setMessage("ok");
			response.setUser(userDTO);
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occours during the registeration " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response login(LoginRequest user){
		Response response = new Response();
		
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			User loginUser = repo.findByEmail(user.getEmail()).orElseThrow(()-> new  OurException("User Not Found"));
			
			String token = jwtUtils.generateToken(loginUser);
			response.setStatusCode(200);
			response.setToken(token);
			response.setRole(loginUser.getRole());
			response.setExpirationTime("7 Days");
			response.setMessage("successfull");
			
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occours during the loggin "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllUsers() {
		
		Response response = new Response();
		
		try {
			List<User> userList = repo.findAll();
			List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setUserList(userDTOList);
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during the getting all user." + e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response getUserBookingHistory(String id) {
		
		Response response = new Response();
		
		try {
			
			User user = repo.findById(Long.valueOf(id)).orElseThrow(() -> new OurException("User Not found"));
			UserDTO userDTO = Utils.mapUserEntityToUserDTOPlusUserBookingAndRooms(user);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setUser(userDTO);
		}
		catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage("Error occurs during the getting booking history." + e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during the getting booking history." + e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response deleteUser(String id) {
		
		Response response = new Response();
		
		try {
			
			repo.findById(Long.valueOf(id)).orElseThrow(() -> new OurException("User Not found"));
			repo.deleteById(Long.valueOf(id));
			response.setStatusCode(200);
			response.setMessage("successfull");
		}
		catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during the getting booking history." + e.getMessage());
		}
		
		return response;	
	}

	@Override
	public Response getUserById(String id) {
		
		Response response = new Response();
		
		try {
			
			User user = repo.findById(Long.valueOf(id)).orElseThrow(() -> new OurException("User Not found"));
			UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setUser(userDTO);
		}
		catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during the getting user." + e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response getMyInfo(String email) {
		
		Response response = new Response();
		
		try {
			
			User user = repo.findByEmail(email).orElseThrow(() -> new OurException("User Not found"));
			UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setUser(userDTO);
		}
		catch(OurException e) {
			response.setStatusCode(404);
			response.setMessage(e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during getting the my info." + e.getMessage());
		}
		
		return response;
	}

}
