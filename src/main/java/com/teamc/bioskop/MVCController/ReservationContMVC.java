package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Service.BookingService;
import com.teamc.bioskop.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@AllArgsConstructor
@Controller
public class ReservationContMVC {

    private final BookingService bookingService;


    @GetMapping("/MVC/Reservations")
    public String showReservationList(Model model) {
        List<Reservation> reservations = bookingService.getAll();
        //alias masuk ke file html
        model.addAttribute("Reservation_masuk", reservations);
        return "Reservation_GetAll"; //ngambil file html
    }
}

