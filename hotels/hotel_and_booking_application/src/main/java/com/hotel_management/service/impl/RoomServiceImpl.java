package com.hotel_management.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel_management.dto.Response;
import com.hotel_management.dto.RoomDTO;
import com.hotel_management.entity.Room;
import com.hotel_management.exception.OurException;
import com.hotel_management.repo.RoomRepository;
import com.hotel_management.service.interfaces.RoomService;
import com.hotel_management.utils.Utils;

@Service
public class RoomServiceImpl implements RoomService{
	
	@Autowired
	private RoomRepository roomRepo;
	

	@Override
	public Response addNewRoom(String photo, String roomType, BigDecimal roomPrice, String roomDescription) {
		
		Response response = new Response();
		
		try {
			Room room = new Room();
			room.setRoomType(roomType);
			room.setRoomPrice(roomPrice);
			room.setRoomPhotoUrl(photo);
			room.setRoomDescription(roomDescription);
			Room saveRoom = roomRepo.save(room);
			
			RoomDTO roomDto = Utils.mapRoomEntityToRoomDTO(saveRoom);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setRoom(roomDto);
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occur during adding new room. "+ e.getMessage());
		}
		return response;
	}

	@Override
	public List<String> getAllRoomType() {
		return roomRepo.findDistinctRoomTypes();
	}

	@Override
	public Response getAllRooms() {
		
		Response response = new Response();
		
		try {
			List<Room> roomList = roomRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setRoomList(roomDTOList);
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occur during getting rooms "+ e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response deleteRoom(long id) {
		
		Response response = new Response();
		
		try {
			roomRepo.findById(id).orElseThrow(() -> new OurException("Room not found!"));
			roomRepo.deleteById(id);
			response.setStatusCode(200);
			response.setMessage("successfull");
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Room cannot delete");
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occour during deleting room "+ e.getMessage());
		}
		
		return response;
	}


	@Override
	public Response getRoomById(long id) {
		
		Response response = new Response();
		
		try {
			Room room = roomRepo.findById(id).orElseThrow(() -> new OurException("Cant' find the room"));
			RoomDTO roomDto = Utils.mapRoomEntityToRoomDTO(room);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setRoom(roomDto);
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Cant't find the room. " + e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occrurs during finding the room "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAvaliableRoomByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		
		Response response = new Response();
		
		try {
			List<Room> roomList = roomRepo.findAllavailbleRoomsByDate(checkInDate, checkOutDate, roomType);
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setRoomList(roomDTOList);
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Cant't find the availble  rooms by data and type. " + e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occrurs during finding the availble rooms by data and type. "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response getAllAvaliableRooms() {
		
		Response response = new Response();
		
		try {
			List<Room> roomList = roomRepo.getAllAvailableRooms();
			List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomListDTO(roomList);
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setRoomList(roomDTOList);
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Cant't find the availble  rooms. " + e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occrurs during finding the availble rooms. "+e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateRoom(long id, String roomType, BigDecimal roomPrice, String photo, String roomDeString) {
		
		Response response = new Response();
		
		try {
			
			Room room = roomRepo.findById(id).orElseThrow(() -> new OurException("Can't found the room"));
			if(roomType != null) room.setRoomType(roomType);
			if(roomPrice != null) room.setRoomPrice(roomPrice);
			if(photo != null) room.setRoomPhotoUrl(photo);
			if(roomDeString != null ) room.setRoomDescription(roomDeString);
			
			Room updateRoom = roomRepo.save(room);
			RoomDTO roomDto = Utils.mapRoomEntityToRoomDTO(updateRoom);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setRoom(roomDto);
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Can't found the room id. " + e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occours during the update room. " + e.getMessage());
		}
		return response;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
