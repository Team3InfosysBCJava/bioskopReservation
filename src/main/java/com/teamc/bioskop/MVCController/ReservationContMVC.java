package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.BookingResponseDTO;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Service.BookingService;
import com.teamc.bioskop.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
@RequestMapping("/Team3/MVC")
public class ReservationContMVC {

    private final BookingService bookingService;

    /**
     * Get All
     */
    @GetMapping("/Reservations")
    public String showReservationList(Model model) {
        List<Reservation> reservations = bookingService.getAll();
        List<BookingResponseDTO> reservationsMaps = new ArrayList<>();
        for (Reservation data:reservations){
            BookingResponseDTO reservationDTO = data.convertToResponse();
            reservationsMaps.add(reservationDTO);
        }
        //alias masuk ke file html
        model.addAttribute("Reservation_entry", reservationsMaps);
        return "Reservation_Index"; //ngambil file html
    }
    /**
     * Get by ID
     */
    @GetMapping("/Reservation/{id}")
    public String showReservationById(@PathVariable("id") Long id, Model model){
        Optional<Reservation> reservation = bookingService.getBookingById(id);
        Reservation reservationGet = reservation.get();
        BookingResponseDTO result = reservationGet.convertToResponse();
        model.addAttribute("Reservation_entry", result);
        return "Reservation_GetById";
    }


    /**
     * Create , status : belum berhasil
     */
    @GetMapping("/Reservation/new")
    public String showRerservationForm(Reservation reservation){
        return "Reservations_New";
    }

    @PostMapping("/Reservation/add")
    public String showAddReservation(@Valid Reservation reservation, BindingResult result, Model model){
        if(result.hasErrors()){
            return "Reservations_New";
        }
        bookingService.createBooking(reservation);
        return "redirect:/Team3/MVC/Reservations";
    }

    /**
     * Delete by ID
     */
    @GetMapping("/Reservation/delete/{id}")
    public String showDeleteReservationById(@PathVariable("id") Long id, Model model){
        Reservation reservation = bookingService.getBookingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        bookingService.deleteSBookingById(id);
        return "Reservation_GetById";
    }

}

