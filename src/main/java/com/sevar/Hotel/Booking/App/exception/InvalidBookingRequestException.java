package com.sevar.Hotel.Booking.App.exception;

public class InvalidBookingRequestException extends RuntimeException {
    public InvalidBookingRequestException(String message){
        super(message);
    }
}
