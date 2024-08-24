package com.hotel_management.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.hotel_management.dto.BookingDTO;
import com.hotel_management.dto.Response;
import com.hotel_management.entity.Booking;
import com.hotel_management.entity.Room;
import com.hotel_management.entity.User;
import com.hotel_management.exception.OurException;
import com.hotel_management.repo.BookingRepository;
import com.hotel_management.repo.RoomRepository;
import com.hotel_management.repo.UserRepository;
import com.hotel_management.service.interfaces.BookingService;
import com.hotel_management.utils.Utils;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private RoomRepository roomRepo;
	
	@Autowired
	private UserRepository userRepo;
	

	@Override
	public Response addBooking(long roomId, long userId, Booking bookingRequest) {
		
		Response response = new Response();
		
		try {
			if(bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
				throw new IllegalArgumentException("Check in date must be before check out date.");
			}
			Room room = roomRepo.findById(roomId).orElseThrow(() -> new OurException("Can't find room"));
			User user = userRepo.findById(userId).orElseThrow(() -> new OurException("Can't find user"));
			
			List<Booking> existingBookings = room.getBookings();
			
			if(!isRoomAvailble(bookingRequest, existingBookings)){
				throw new OurException("Room is not availble selected date.");
			}
			
			bookingRequest.setRoom(room);
			bookingRequest.setUser(user);
			
			String bookingConfirmationCode = Utils.generateAlphanumericString(12);
			bookingRequest.setBookingConfirmationCode(bookingConfirmationCode);
			
			bookingRepo.save(bookingRequest);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setBookingConfirmationCode(bookingConfirmationCode);
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occours during booking request. " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response findBookingbyConfirmationCode(String comfirmationCode) {
		
		Response response = new Response();
		
		try {
			
			Booking booking = bookingRepo.findByBookingConfirmationCode(comfirmationCode).orElseThrow(() -> new OurException("Can't found booking with "+comfirmationCode));
			BookingDTO bookingDto = Utils.mapBookingEntityToBookingDTO(booking);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setBooking(bookingDto);
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Can't found the booking " + e.getMessage());
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occours during the finding the comfirmation code "+ e.getMessage());
		}
		
		return response;
	}

	@Override
	public Response getAllBooking() {
		
		Response response = new Response();
		
		try {
			List<Booking> bookings = bookingRepo.findAll(Sort.by(Sort.Direction.DESC,"id"));
			List<BookingDTO> bookingsDto = Utils.mapBookingListEntityToBookingListDTO(bookings);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
			response.setBookingList(bookingsDto);
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during getting booking list. " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response cancelBooking(long bookingId) {
		
		Response response = new Response();
		
		try {
			bookingRepo.findById(bookingId).orElseThrow(() -> new OurException("Can't find the booking."));
			bookingRepo.deleteById(bookingId);
			
			response.setStatusCode(200);
			response.setMessage("successfull");
		}
		catch(OurException e) {
			response.setStatusCode(400);
			response.setMessage("Can't delete the booking");
		}
		catch(Exception e) {
			response.setStatusCode(500);
			response.setMessage("Error occurs during cancel booking. " + e.getMessage());
		}
		return response;
	}
	
	private boolean isRoomAvailble(Booking bookingRequest, List<Booking> existingBookings) {
		
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
                                || bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
                                || (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
                                && bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
                                || (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

                                && bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

                                || (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
                                && bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate()))
                );
    }


}
