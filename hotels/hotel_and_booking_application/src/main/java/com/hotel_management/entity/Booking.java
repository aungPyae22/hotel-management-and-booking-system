package com.hotel_management.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "bookings")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull(message = "check in date is require.")
	private LocalDate checkInDate;
	
	@Future(message = "check out date must be future.")
	private LocalDate checkOutDate;
	
	@Min(value = 1,message = "number of audits value must greater than 1")
	private int numOfAudits;
	@Min(value = 0, message = "number of children value must have at least 0.")
	private int numOfChildren;
	private int totalNumOfGuest;
	
	private String bookingConfirmationCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_id")
	private Room room;
	
	
	private void calculateTotalNumOfGuest() {
		this.totalNumOfGuest = this.numOfAudits + this.numOfChildren;
	}


	public int getNumOfAudits() {
		return numOfAudits;
	}


	public void setNumOfAudits(int numOfAudits) {
		this.numOfAudits = numOfAudits;
		calculateTotalNumOfGuest();
	}


	public int getNumOfChildren() {
		return numOfChildren;
	}


	public void setNumOfChildren(int numOfChildren) {
		this.numOfChildren = numOfChildren;
		calculateTotalNumOfGuest();
	}
	
	


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public LocalDate getCheckInDate() {
		return checkInDate;
	}


	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}


	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}


	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}


	public int getTotalNumOfGuest() {
		return totalNumOfGuest;
	}


	public void setTotalNumOfGuest(int totalNumOfGuest) {
		this.totalNumOfGuest = totalNumOfGuest;
	}


	public String getBookingConfirmationCode() {
		return bookingConfirmationCode;
	}


	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}


	@Override
	public String toString() {
		return "Booking [id=" + id + 
				", checkInDate=" + checkInDate + 
				", checkOutDate=" + checkOutDate + 
				", numOfAudits=" + numOfAudits + 
				", numOfChildren=" + numOfChildren + 
				", totalNumOfGuest=" + totalNumOfGuest + 
				", bookingConfirmationCode=" + bookingConfirmationCode + "]";
	}
	
	
	
	
}
