package com.teamc.bioskop.Service;

import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Repository.BookingRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService{

    private BookingRepository bookingRepository;
    public static Integer currentPage;

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

    @Override
    public Page<Reservation> getBookingFilm(String name, String page) {
        Integer pageNumber = null;

        //check null pointer
        if(page != null){
            pageNumber = pageUpdate(page);
        }
        if(name != null){
            return bookingRepository.getBookingFilm(name, null);
        }else if(page == null){
            return bookingRepository.findAll(PageRequest.of(0,10,Sort.by("schedule.films.name")));
        }else{
            return bookingRepository.findAll(PageRequest.of(pageNumber,10,Sort.by("schedule.films.name")));
        }
    }

    @Override
    public Page<Reservation> getBookingId(Long id, String page) {
        Integer pageNumber = null;

        //check null pointer
        if(page != null){
            pageNumber = pageUpdate(page);
        }
        if(id != null){
            return bookingRepository.getBookingId(id, null);
        }else if(page == null){
            return bookingRepository.findAll(PageRequest.of(0,10,Sort.by("reservationId")));
        }else{
            return bookingRepository.findAll(PageRequest.of(pageNumber,10,Sort.by("reservationId")));
        }
    }


    @Override
    public Page<Reservation> search(String keyword, String page) {
        Integer pageNumber = null;
        if(page != null){
            pageNumber = pageUpdate(page);
        }
        if(keyword != null){
            return bookingRepository.search(keyword,null);
        }else if(page == null){
            return bookingRepository.findAll(PageRequest.of(0,10,Sort.by("reservationId")));
        }else{
         return bookingRepository.findAll(PageRequest.of(pageNumber,10,Sort.by("reservationId")));
        }
    }

    @Override
    public Integer pageUpdate(String page) {

        //container
        Integer pageNumber = null;

        //check params
        if(page.equals("prev")){
            currentPage--;
        }
        else if(page.equals("next")){
            currentPage++;
        } else{
            currentPage = Integer.parseInt(page);
        }

        if(currentPage == 0){
            currentPage = 1;
        }
        //page in bootstrap template starts from 0
        pageNumber = currentPage-1;
        return pageNumber;
    }

    @Override
    public Page<Reservation> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.bookingRepository.findAll(pageable);
    }


}