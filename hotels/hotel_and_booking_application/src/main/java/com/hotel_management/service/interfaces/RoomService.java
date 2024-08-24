package com.hotel_management.service.interfaces;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.hotel_management.dto.Response;

public interface RoomService {
	
	Response addNewRoom(String photo, String roomType, BigDecimal roomPrice, String roomDescription);
	
	List<String> getAllRoomType();
	
	Response getAllRooms();
	
	Response deleteRoom(long id);
	
	Response updateRoom(long id, String roomType, BigDecimal roomPrice, String photo, String roomDeString);
	
	Response getRoomById(long id);
	
	Response getAvaliableRoomByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType);
	
	Response getAllAvaliableRooms();
}
