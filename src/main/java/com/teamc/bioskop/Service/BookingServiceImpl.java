package com.teamc.bioskop.Service;

import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.Seats;
import com.teamc.bioskop.Repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;

    @Override
    public List<Reservation> getAll() {
        List<Reservation> optionalBooking = bookingRepository.findAll();
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Data Reservation not exist");
        }
        return this.bookingRepository.findAll();
    }

    @Override
    public Optional<Reservation> getBookingById(Long Id) throws ResourceNotFoundException {
        Optional<Reservation> optionalBooking = bookingRepository.findById(Id);
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Reservation not exist with id " + Id);
        }
        return this.bookingRepository.findById(Id);
    }

    @Override
    public Reservation updateBooking(Reservation booking) throws ResourceNotFoundException {
        Optional<Reservation> optionalBooking = bookingRepository.findById(booking.getReservationId());
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Reservation not exist with id " + booking.getReservationId());
        }
        return this.bookingRepository.save(booking);
    }

    @Override
    public Reservation getReferenceById(Long Id) {
        return this.bookingRepository.getReferenceById(Id);
    }

    @Override
    public Reservation createBooking(Reservation booking) {
          return this.bookingRepository.save(booking);
    }

    @Override
    public void deleteSBookingById(Long Id) throws ResourceNotFoundException{
        Optional<Reservation> optionalBooking = bookingRepository.findById(Id);
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Reservation not exist with id " + Id);
        }
        Reservation booking = bookingRepository.getReferenceById(Id);
        this.bookingRepository.delete(booking);
    }

    //custom select
    @Override
    public List<Reservation> getBookingByFilmName(String name) {
        List<Reservation> optionalBooking = bookingRepository.getBookingByFilmName(name);
        if(optionalBooking.isEmpty()){
            throw new ResourceNotFoundException("Reservation not exist with Filmname " +name);
        }
        return this.bookingRepository.getBookingByFilmName(name);
    }

}
