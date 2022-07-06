package com.teamc.bioskop.Controller;

import com.teamc.bioskop.DTO.BookingRequestDTO;
import com.teamc.bioskop.DTO.BookingResponseDTO;
import com.teamc.bioskop.DTO.BookingResponsePost;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.*;
import com.teamc.bioskop.Response.ResponseHandler;
import com.teamc.bioskop.Service.*;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teamC/v1/DTO")
@AllArgsConstructor
public class ReservationController {

    private static final Logger logger = LogManager.getLogger(ReservationController.class);
    private static final String Line = "====================";
    private BookingService bookingService;
    private ScheduleService scheduleService;
    private SeatsService seatsService;


    /**
     *Get All booking from booking table
     * throws ResourceNotFoundException if not found happened
     */
    @GetMapping("/Reservation")
    public ResponseEntity<Object> findAll(){
        try {
            List<Reservation> bookings = bookingService.getAll();
            List<BookingResponseDTO> bookingmaps = new ArrayList<>();
            logger.info(Line + " Logger Start Find All Reservation " + Line);
            for (Reservation dataresults:bookings){
                logger.info("Reservation id : "+dataresults.getReservationId()+
                        ", Title Film : "+dataresults.getSchedule().getFilms().getName()+
                        ", Status Playing : "+dataresults.getSchedule().getFilms().getIsPlaying()+
                        ", Studio : "+dataresults.getSchedule().getSeats().getStudioName()+
                        ", Seat Number : "+dataresults.getSchedule().getSeats().getSeatNumber()+
                        ", Status Seat : "+dataresults.getSchedule().getSeats().getIsAvailable()+
                        ", Price : "+dataresults.getSchedule().getPrice()+
                        ", Date Show : "+dataresults.getSchedule().getDateShow()+
                        ", Show Start : "+dataresults.getSchedule().getShowStart()+
                        ", Show End : "+dataresults.getSchedule().getShowEnd());
                BookingResponseDTO bookDTO = dataresults.convertToResponse();
                bookingmaps.add(bookDTO);
            }
            logger.info(Line + " Logger End Find All Reservation " + Line);
            return ResponseHandler.generateResponse("Success Get All",HttpStatus.OK,bookingmaps);
        }catch(ResourceNotFoundException e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        }
    }

    /**
     *Get Booking by Booking id
     * throws ResourceNotFoundException if data is not found
     */
    @GetMapping("/Reservation/{id}")
    public ResponseEntity<Object> getBookingById(@PathVariable Long id){
        try {
            Optional<Reservation> reservation = bookingService.getBookingById(id);
            Reservation reservationGet = reservation.get();
            BookingResponseDTO result = reservationGet.convertToResponse();
            logger.info(Line + " Logger Start Get By id Reservation " + Line);
            logger.info("Update RReservation : " + result);
            logger.info(Line + " Logger End Get By id Reservation " + Line);
            return ResponseHandler.generateResponse("Success Get By Id",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * update booking
     * throws ResourceNotFoundException if data not found
     */
    @PutMapping("/Reservation/{id}")
    public ResponseEntity<Object> bookingupdate(@PathVariable Long id, @RequestBody BookingRequestDTO bookingRequestDTO){
        try {
            if(bookingRequestDTO.getSchedule() == null || bookingRequestDTO.getUser() == null){
                throw new ResourceNotFoundException("Reservation must have schedule id and user id");
            }
            Reservation booking = bookingRequestDTO.covertToEntitiy();
            booking.setReservationId(id);
            Reservation bookingUpdate = bookingService.updateBooking(booking);
            BookingResponseDTO results = bookingUpdate.convertToResponse();
            logger.info(Line + " Logger Start Update Reservation " + Line);
            logger.info("Update Booking : " + bookingUpdate);
            logger.info(Line + " Logger End Update Reservation " + Line);
            return ResponseHandler.generateResponse("Success Update Reservation",HttpStatus.CREATED,results);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Bad Request");
        }
    }
    /**
     *create new booking into booking table
     * throws ResourceNotFoundException if bad request happened
     */
    @PostMapping("/Reservation")
    public ResponseEntity<Object> bookingCreate(@RequestBody BookingRequestDTO bookingRequestDTO){
        try{
            if(bookingRequestDTO.getSchedule() == null || bookingRequestDTO.getUser() == null){
                throw new ResourceNotFoundException("Reservation must have schedule id and user id");
            }
            Reservation booking = bookingRequestDTO.covertToEntitiy();
            bookingService.createBooking(booking);
            BookingResponsePost result = booking.convertToResponsePost();
            logger.info(Line + " Logger Start Create Reservation " + Line);
            logger.info("Create Booking : " + result);
            logger.info(Line + " Logger End Create Reservation " + Line);
            return ResponseHandler.generateResponse("Success Create Reservation",HttpStatus.CREATED,result);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Create Database");
        }
    }

    /**
     * delete schedule by id
     * throws ResourceNotFoundException if data is not found
     */
    @DeleteMapping("/Reservation/{id}")
    public ResponseEntity<Object> deleteBooking(@PathVariable Long id){
        try {
            bookingService.deleteSBookingById(id);
            Boolean result = Boolean.TRUE;
            logger.info(Line + " Logger Start Delete Reservation " + Line);
            logger.info("Success Delete Reservatiom by ID :"+result);
            logger.info(Line + " Logger End Delete Reservation " + Line);
            return ResponseHandler.generateResponse("Success Delete Reservation by ID",HttpStatus.OK,result);
        }catch(ResourceNotFoundException e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Data not found");
        }
    }

    /**
     * custom Query Booking by filmname
     * Query Find by Filmnme
     *throws ResourceNotFoundException if film name is not found
     */
    @PostMapping("/Reservation/Filmname")
    public ResponseEntity<Object> findBookingBySchdeuleFilmName(@RequestBody Films films){
        try {
            List<Reservation> bookingByScheduleFilmsname = bookingService.getBookingByFilmName(films.getName());
            List<BookingResponseDTO> bookingResponseDTOS = bookingByScheduleFilmsname.stream()
                    .map(Reservation::convertToResponse)
                    .collect(Collectors.toList());
            logger.info(Line+" Logger Start Query By Film Name Reservation "+Line);
            logger.info("Success Query By Filmname : " +bookingResponseDTOS);
            logger.info(Line+" Logger End Query By Film Name Reservation "+Line);
            return ResponseHandler.generateResponse("Succes Query By Filmname",HttpStatus.OK,bookingResponseDTOS);
        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST,"Failed Query By Filename");
        }

    }


}





