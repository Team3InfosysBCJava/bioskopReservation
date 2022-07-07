package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.ScheduleResponseDTO;
import com.teamc.bioskop.DTO.SeatsResponseDTO;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.Seats;
import com.teamc.bioskop.Service.SeatsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.Id;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class SeatsContMVC {
    private final SeatsService seatsService;

    //HOMEPAGE
    @GetMapping("/MVC/Seats/Home")
    public String showHomepage(){
        return "Homepage";
    }


    //CREATE
    @GetMapping("/MVC/Seats/new-seats")
    public String showSeatsForm(Seats seats) {
        return "Seats_AddNew";
    }

    @PostMapping("/MVC/Seats/add-seats")
    public String showAddSeats(@Valid Seats seats, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "Seats_AddNew";
        }

        seatsService.createseat(seats);
        return "redirect:/MVC/Seats";
    }


    //GET ALL
    @GetMapping("/MVC/Seats")
    public String showSeatsList(Model model){
        List<Seats> result = seatsService.findAllseats();
        model.addAttribute("seats_entry", result);
        return "Seat_Index";
    }


    //GET BY ID
    @GetMapping("/MVC/Seats/{id}")
    public String showSeatsById(@PathVariable("id") Long id, Model model){

        Optional<Seats> seats = seatsService.findbyid(id);
        Seats seatget = seats.get();
        SeatsResponseDTO results = seatget.convertToResponses();
        model.addAttribute("seats_entry", results);
        return "Seats_GetById";
    }


    //UPDATE
    @GetMapping("/MVC/Seats/update/{id}")
    public String showEditSeatsForm(@PathVariable("id") Long id, Model model){
        Optional<Seats> seats = seatsService.findbyid(id);
        Seats seatsget = seats.get();
        model.addAttribute("seats", seatsget);
        return "Seats_Update";
    }

    @PostMapping("/MVC/Seats/update-seats/{id}")
    public String showUpdateSeats(@PathVariable("id") Long id, @Valid Seats seats, BindingResult result, Model model){
        if (result.hasErrors()) {
            seats.setSeatId(id);
            return "Seats_Update";
        }

        seats.setSeatId(id);
        seatsService.updateseat(seats, id);
        return "redirect:/MVC/Seats";
    }


    //DELETE BY ID
    @GetMapping("/MVC/Seats/delete/{id}")
    public String showDeleteSeats(@PathVariable("id") Long id, Model model) {
//        Seats seats = seatsService.getSeatsById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid seats Id:" + id));
        seatsService.deleteseat(id);
        return "redirect:/MVC/Seats";
    }
}
