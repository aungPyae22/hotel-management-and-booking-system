package com.hotel_management.service.interfaces;

import com.hotel_management.dto.Response;
import com.hotel_management.entity.Booking;

public interface BookingService {
	
	Response addBooking(long roomId, long userId, Booking bookingRequest);
	
	Response findBookingbyConfirmationCode(String comfirmationCode);
	
	Response getAllBooking();
	
	Response cancelBooking(long bookingId);
}
