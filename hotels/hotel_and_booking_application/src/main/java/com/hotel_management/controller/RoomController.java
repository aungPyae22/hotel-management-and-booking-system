package com.hotel_management.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_management.dto.Response;
import com.hotel_management.service.interfaces.RoomService;

@RestController
@RequestMapping(path = "/htms/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	
	@PostMapping(path = "/add")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> addNewRoom(
				@RequestParam(value = "roomPhotoUrl", required = false) String phtoUrl,
				@RequestParam(value ="roomType", required = false) String roomType,
				@RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
				@RequestParam(value = "roomDescription", required = false) String roomDescription
			){
		
		if(phtoUrl.isEmpty() || roomType.isBlank() || roomType == null || roomPrice == null ) {
			Response response = new Response();
			response.setStatusCode(400);
			response.setMessage("Please enter the related field(photo,room-type,room-price,room-description)");
		}
		Response response = roomService.addNewRoom(phtoUrl, roomType, roomPrice, roomDescription);
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	
	@GetMapping(path = "/all")
	public ResponseEntity<Response> getAllRoom(){
		Response response = roomService.getAllRooms();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping(path = "/all-avaliable-room")
	public ResponseEntity<Response> getAvailbleRoom(){
		Response response = roomService.getAllAvaliableRooms();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping(path = "/types")
	public List<String> getRoomTypes(){
		return roomService.getAllRoomType();
				
	}
	
	@PostMapping(path = "/room-by-id/{roomId}")
	public ResponseEntity<Response> getRoomById(@PathVariable long roomId){
		Response response = roomService.getRoomById(roomId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PostMapping(path = "/availble-room-by-date-and-type")
	public ResponseEntity<Response> avilbleRoomByDateAndType(
				@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
				@RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
				@RequestParam( required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String roomType
			){
		
		if(checkInDate == null || roomType.isBlank() || roomType == null || checkOutDate == null ) {
			Response response = new Response();
			response.setStatusCode(400);
			response.setMessage("Please enter the related field(checkInDate,checkOutDate,roomtype)");
		}
		Response response = roomService.getAvaliableRoomByDataAndType(checkInDate, checkOutDate, roomType);
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@PutMapping(path = "/update/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> updateRoom(@PathVariable("roomId") long roomId,
												@RequestParam(value = "roomPhotoUrl", required = false) String photoUrl,
												@RequestParam(value ="roomType", required = false) String roomType,
												@RequestParam(value = "roomPrice", required = false) BigDecimal roomPrice,
												@RequestParam(value = "roomDescription", required = false) String roomDescription
												){
		Response response = roomService.updateRoom(roomId, roomType, roomPrice, photoUrl, roomDescription);
		
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@DeleteMapping(path = "/delete/{roomId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> deleteRoom(@PathVariable long roomId){
		
		Response response = roomService.deleteRoom(roomId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
