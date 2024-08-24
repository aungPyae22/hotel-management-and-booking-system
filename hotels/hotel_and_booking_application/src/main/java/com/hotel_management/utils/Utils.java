package com.hotel_management.utils;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;


import com.hotel_management.dto.BookingDTO;
import com.hotel_management.dto.RoomDTO;
import com.hotel_management.dto.UserDTO;
import com.hotel_management.entity.Booking;
import com.hotel_management.entity.Room;
import com.hotel_management.entity.User;

public class Utils {
	
	private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLNMOPQRSTUVWHYZ0123456789";
	
	private static final SecureRandom RANDON = new SecureRandom();
	
	public static String generateAlphanumericString(int length) {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (int i = 0; i < length ; i++) {
			int randonIndex = RANDON.nextInt(ALPHANUMERIC_STRING.length());
			char randonChar = ALPHANUMERIC_STRING.charAt(randonIndex);
			stringBuilder.append(randonChar);
		}
		return stringBuilder.toString();
	}
	
	public static UserDTO mapUserEntityToUserDTO(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRole());
		
		return userDto;
		
	}
	
	public static RoomDTO mapRoomEntityToRoomDTO(Room room) {
		RoomDTO roomDto = new RoomDTO();
		roomDto.setId(room.getId());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomPrice(room.getRoomPrice());
		roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
		roomDto.setRoomDescription(room.getRoomDescription());
		
		return roomDto;
	}
	
	
	
	public static UserDTO mapUserEntityToUserDTOPlusUserBookingAndRooms(User user) {
		UserDTO userDto = new UserDTO();
		userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());
		userDto.setPhoneNumber(user.getPhoneNumber());
		userDto.setRole(user.getRole());
		
		if(!user.getBookings().isEmpty()) {
			userDto.setBooking(user.getBookings().stream().map(booking -> 
									mapBookingEntityToBookingDTOplusBookdeRoom(booking,false)).collect(Collectors.toList()));
		}
		
		return userDto;
	}
	
	public static RoomDTO mapRoomEntityToRoomDTOPlusBookings(Room room) {
		RoomDTO roomDto = new RoomDTO();
		roomDto.setId(room.getId());
		roomDto.setRoomType(room.getRoomType());
		roomDto.setRoomPrice(room.getRoomPrice());
		roomDto.setRoomPhotoUrl(room.getRoomPhotoUrl());
		roomDto.setRoomDescription(room.getRoomDescription());
		
		if(!room.getBookings().isEmpty()) {
			roomDto.setBookings(room.getBookings().stream().map(
									Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList()));
		}
		
		return roomDto;
	}
	
	public static BookingDTO mapBookingEntityToBookingDTO(Booking booking) {
		BookingDTO bookingDto = new BookingDTO();
		
		bookingDto.setId(booking.getId());
		bookingDto.setCheckInDate(booking.getCheckInDate());
		bookingDto.setCheckOutDate(booking.getCheckOutDate());
		bookingDto.setNumOfAudits(booking.getNumOfAudits());
		bookingDto.setNumOfChildren(booking.getNumOfChildren());
		bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());
		bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		return bookingDto;
	}

	public static BookingDTO mapBookingEntityToBookingDTOplusBookdeRoom(Booking booking, boolean mapUser) {
		BookingDTO bookingDto = new BookingDTO();
		
		bookingDto.setId(booking.getId());
		bookingDto.setCheckInDate(booking.getCheckInDate());
		bookingDto.setCheckOutDate(booking.getCheckOutDate());
		bookingDto.setNumOfAudits(booking.getNumOfAudits());
		bookingDto.setNumOfChildren(booking.getNumOfChildren());
		bookingDto.setTotalNumOfGuest(booking.getTotalNumOfGuest());
		bookingDto.setBookingConfirmationCode(booking.getBookingConfirmationCode());
		
		if(mapUser) {
			bookingDto.setUser(Utils.mapUserEntityToUserDTO(booking.getUser()));
		}
		
		if(booking.getRoom() != null) {
			RoomDTO roomDto = new RoomDTO();
			roomDto.setId(booking.getRoom().getId());
			roomDto.setRoomType(booking.getRoom().getRoomType());
			roomDto.setRoomPrice(booking.getRoom().getRoomPrice());
			roomDto.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
			roomDto.setRoomDescription(booking.getRoom().getRoomDescription());
			bookingDto.setRoom(roomDto);
		}
		
		return bookingDto;
		
	}
	
	
	public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){
		return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
	}
	
	public static List<RoomDTO> mapRoomListEntityToRoomListDTO(List<Room> roomList){
		return roomList.stream().map(Utils::mapRoomEntityToRoomDTO).collect(Collectors.toList());
	}
	
	public static List<BookingDTO> mapBookingListEntityToBookingListDTO(List<Booking> bookingList){
		return bookingList.stream().map(Utils::mapBookingEntityToBookingDTO).collect(Collectors.toList());
	}
	
	
	
	
	
	
	
	
	
	
	
}
