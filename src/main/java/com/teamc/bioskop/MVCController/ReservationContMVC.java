package com.teamc.bioskop.MVCController;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.teamc.bioskop.DTO.BookingResponseDTO;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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
     * Search anything , default get all
     */
    @GetMapping("/MVC/Reservations")
    public String search(Model model, @Param("keyword") String keyword, @Param("page") String page){

        Integer pageNumber = null;

        //check null pointer
        if(page != null){
            pageNumber = bookingService.pageUpdate(page);
        }

        //Pagination
        Page<Reservation> results = bookingService.search(keyword,pageNumber);

        model.addAttribute("Reservation_entry",results);
        model.addAttribute("keyword",keyword);
        model.addAttribute("reservation",new Reservation());
        return "Index_Reservation";
    }

    /**
     * Search by ID
     */
    @GetMapping("/MVC/Reservation/Id")
    public String search(Model model, @Param("id") long id, @Param("page") String page){
        Integer pageNumber = null;

        //check null pointer
        if(page != null){
            pageNumber = bookingService.pageUpdate(page);
        }

        //Pagination
        Page<Reservation> results = bookingService.getBookingId(id,pageNumber);
        model.addAttribute("Reservation_entry", results);
        model.addAttribute("id",id);
        model.addAttribute("reservation",new Reservation());
        
     
        return "Index_Reservation";
    }

    /**
     * Search filmname
     */
    @PostMapping("/MVC/Reservation/searchFilm")
    public String searchFilm(Films film, Model model, String name,@Param("page") String page) {
        Integer pageNumber = null;

        //check null pointer
        if(page != null){
            pageNumber = bookingService.pageUpdate(page);
        }

        //Pagination
        if(name!=null) {
            Page<Reservation> results = bookingService.getBookingFilm(film.getName(),pageNumber);
            model.addAttribute("Reservation_entry", results);
            model.addAttribute("reservation",new Reservation());
        }
        // else {
        //     return "redirect:/MVC/Reservations";
        // }
        return "Index_Reservation";
    }

    /**
     * Create
     */
    @PostMapping("/MVC/Reservation/add")
    public String showAddReservation(@Valid Reservation reservation, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/MVC/Reservations";
        }
        bookingService.createBooking(reservation);
        return "redirect:/MVC/Reservations";
    }

    /**
     * Update
      */
    @PostMapping("/MVC/Reservation/update-reservation/{id}")
    public String showUpdateReservation(@PathVariable("id") Long id, @Valid Reservation reservation, BindingResult result, Model model){
        if (result.hasErrors()){
            reservation.setReservationId(id);
            return "redirect:/MVC/Reservations";
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



}

/** Coding Jounrey
 * Update By Id
 * 
 * @GetMapping("/MVC/Reservation/update/{id}")
    public String showEditReservationForm(@PathVariable("id") Long id, Model model){
    Optional<Reservation> reservation = bookingService.getBookingById(id);
    Reservation reservationget = reservation.get();
    model.addAttribute("reservation", reservationget);
    return "Reservation_Update";}
    
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

    * Create 
    @GetMapping("/MVC/Reservation/new")
    public String showRerservationForm(Reservation reservation){

        return "Reservations_New";
    }

    @PostMapping("/MVC/Reservation/add")
    public String showAddReservation(@Valid Reservation reservation, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/MVC/Reservations";
        }
        bookingService.createBooking(reservation);
        return "redirect:/MVC/Reservations";
    }

    * Get By Id 
   @GetMapping("/MVC/Reservation/{id}")
   public String showReservationById(@PathVariable("id") Long id, Model model){
       Optional<Reservation> reservation = bookingService.getBookingById(id);
       Reservation reservationGet = reservation.get();
       BookingResponseDTO result = reservationGet.convertToResponse();
       model.addAttribute("Reservation_entry", result);
       return "Reservation_GetById";
   }
    @GetMapping("/MVC/Reservation/Id")
    public String searchbyID(Model model, @Param("id") Long id){
        Reservation reservation = bookingService.getReferenceById(id);
        BookingResponseDTO result = reservation.convertToResponse();
        model.addAttribute("Reservation_entry", result);
        model.addAttribute("id",id);
        model.addAttribute("reservation",new Reservation());
        return "Index_Reservation";
    }
    <form class="d-flex" role="search" th:action="@{/MVC/Reservation/Id}">
    <input type="text" name="id" id="id" th:value="${id}" placeholder="ID" required>
    <button input class="btn" type="button" id="btnClear2" onclick="clearSearch()">
    <i class='bx bx-refresh icon'></i></button>
    </form>
 */
