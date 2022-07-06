package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.BookingResponseDTO;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Controller
public class ReservationContMVC {

    private final BookingService bookingService;

    /**
     * Search filmname , default get all
     */
    @GetMapping("/MVC/Reservations")
    public String search(Model model, @Param("keyword") String keyword){
        List<Reservation> reservations = bookingService.search(keyword);
        List<BookingResponseDTO> results = reservations.stream()
                .map(Reservation::convertToResponse)
                .collect(Collectors.toList());
//        Collections.reverse(results);
        model.addAttribute("Reservation_entry",results);
        model.addAttribute("keyword",keyword);
        return "Reservation_Index";
    }

    /**
     * Get by ID
     */
    @GetMapping("/MVC/Reservation/Id")
    public String searchbyID(Model model, @Param("id") Long id){
        Reservation reservation = bookingService.getReferenceById(id);
        BookingResponseDTO result = reservation.convertToResponse();
        model.addAttribute("Reservation_entry", result);
        model.addAttribute("id",id);
        return "Reservation_Index";
    }
//    @GetMapping("/MVC/Reservation/{id}")
//    public String showReservationById(@PathVariable("id") Long id, Model model){
//        Optional<Reservation> reservation = bookingService.getBookingById(id);
//        Reservation reservationGet = reservation.get();
//        BookingResponseDTO result = reservationGet.convertToResponse();
//        model.addAttribute("Reservation_entry", result);
//        return "Reservation_GetById";
//    }

    /**
     * Create
     */
    @GetMapping("/MVC/Reservation/new")
    public String showRerservationForm(Reservation reservation){

        return "Reservations_New";
    }

    @PostMapping("/MVC/Reservation/add")
    public String showAddReservation(@Valid Reservation reservation, BindingResult result, Model model){
        if(result.hasErrors()){
            return "Reservations_New";
        }
        bookingService.createBooking(reservation);
        return "redirect:/MVC/Reservations";
    }

    /**
     * Update
      */
    @GetMapping("/MVC/Reservation/update/{id}")
    public String showEditReservationForm(@PathVariable("id") Long id, Model model){
        Optional<Reservation> reservation = bookingService.getBookingById(id);
        Reservation reservationget = reservation.get();
        model.addAttribute("reservation", reservationget);
        return "Reservation_Update";
    }

    @PostMapping("/MVC/Reservation/update-reservation/{id}")
    public String showUpdateReservation(@PathVariable("id") Long id, @Valid Reservation reservation, BindingResult result, Model model){
        if (result.hasErrors()){
            reservation.setReservationId(id);
            return "Reservation_Update";
        }
        reservation.setReservationId(id);
        bookingService.updateBooking(reservation);
        return "redirect:/MVC/Reservations";
    }

    /**
     * Delete by ID
     */
    @GetMapping("/MVC/Reservation/delete/{id}")
    public String showDeleteReservationById(@PathVariable("id") Long id, Model model){
        Reservation reservation = bookingService.getBookingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        bookingService.deleteSBookingById(id);
        return "redirect:/MVC/Reservations";
    }
    /**
     * Search filmname
     */
    @PostMapping("/MVC/Reservation/searchFilm")
    public String searchFilm(Films film, Model model, String name) {
    if(name!=null) {
        List<Reservation> reservations = bookingService.getBookingByFilmName(film.getName());
        //pake stream
        List<BookingResponseDTO> results = reservations.stream()
                .map(Reservation::convertToResponse)
                .collect(Collectors.toList());
//        Collections.reverse(results);
        model.addAttribute("Reservation_entry", results);
    }else {
        List<Reservation> reservations = bookingService.getAll();
        List<BookingResponseDTO> reservationsMaps = new ArrayList<>();
        //manual for each
        for (Reservation data:reservations){
            BookingResponseDTO reservationDTO = data.convertToResponse();
             reservationsMaps.add(reservationDTO);
        }
//        Collections.reverse(reservationsMaps);
        model.addAttribute("Reservation_entry", reservationsMaps);
    }
    return "Reservation_Index";
    }

}

