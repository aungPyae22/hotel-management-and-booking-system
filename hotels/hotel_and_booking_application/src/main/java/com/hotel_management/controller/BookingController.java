package com.hotel_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel_management.dto.Response;
import com.hotel_management.entity.Booking;
import com.hotel_management.service.interfaces.BookingService;

@RestController
@RequestMapping(path = "/htms/bookings")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	
	@PostMapping(path = "/book-room/{roomId}/{userId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> saveBooking(@PathVariable long roomId,
												@PathVariable long userId,
												@RequestBody Booking booking)
	{
		Response response = bookingService.addBooking(roomId, userId, booking);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	@GetMapping(path = "/all")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Response> getAllBookings(){
		Response response = bookingService.getAllBooking();
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@GetMapping(path = "/booking-by-confirmationCode/{confirmationCode}")
	public ResponseEntity<Response> getBookingByConfirmationCode(@PathVariable String confirmationCode){
		Response response = bookingService.findBookingbyConfirmationCode(confirmationCode);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	@DeleteMapping(path = "/cancel/{bookingId}")
	@PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
	public ResponseEntity<Response> cancelBooking(@PathVariable long bookingId){
		Response response = bookingService.cancelBooking(bookingId);
		return ResponseEntity.status(response.getStatusCode()).body(response);
	}
	
	
	
	
	
	
	
	
	
}
