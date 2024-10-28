package com.sevar.Hotel.Booking.App.service;

import com.sevar.Hotel.Booking.App.model.BookedRoom;

import java.util.List;

public interface BookingService {
    void cancelBooking(Long bookingId);

    String saveBooking(Long roomId, BookedRoom bookingRequest);

    BookedRoom findByBookingConfirmationCode(String confirmationCode);

    List<BookedRoom> getAllBookings();

    List<BookedRoom> getBookingsByUserEmail(String email);
}
