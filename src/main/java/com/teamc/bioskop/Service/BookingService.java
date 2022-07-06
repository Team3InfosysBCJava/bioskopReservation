package com.teamc.bioskop.Service;

import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.Seats;

import java.util.List;
import java.util.Optional;

public interface BookingService {

    List<Reservation> getAll();
    Optional<Reservation> getBookingById(Long Id);
    Reservation createBooking(Reservation booking);
    void deleteSBookingById(Long Id);
    Reservation updateBooking(Reservation booking);
    Reservation getReferenceById (Long Id);
    List<Reservation> getBookingByFilmName(String name);
    List<Reservation> search(String keyword);
}
