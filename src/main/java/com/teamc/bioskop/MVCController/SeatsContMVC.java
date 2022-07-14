package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.FilmsResponseDTO;
import com.teamc.bioskop.DTO.ScheduleResponseDTO;
import com.teamc.bioskop.DTO.SeatsResponseDTO;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.Seats;
import com.teamc.bioskop.Service.SeatsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
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
    public String showSeatsList(Model model, @Param("keyword") String keyword, @Param("page") String page) {
       Integer pageNumber = null;

       //check null pointer
        if (page != null){
            pageNumber = seatsService.pageUpdate(page);
        }

        //Pagination
        Page<Seats> result = seatsService.searchByNumber(keyword,pageNumber);

        //List<Seats>seats = seatsServices.searchByNumber(keyword,pageNumber);

        //alias masuk ke file Html
        model.addAttribute("seats_entry", result);
        model.addAttribute("keyword",keyword);
        model.addAttribute("seat_add", new Seats());
        return "Seat_Index"; //ngambil file Html
    }


    //GET BY ID
    @GetMapping("/MVC/Seats/Id")
    public String showSeatsById(Model model,@Param("id") Long Id){
    Seats seats = seatsService.getReferenceById(Id);
        model.addAttribute("seats", seats);
        model.addAttribute("id", Id);
        model.addAttribute("seats_entry", new Seats());
        return "Seats_GetById";
    }

//    @GetMapping("/MVC/Seats/{id}")
//    public String showSeatById(@PathVariable("id") Long id, Model model) {
//
//        Optional<Seats> seats = seatsService.findbyid(id);
//        Long integerId = null;
//        Seats seatsget = seats.get();
//        SeatsResponseDTO result = new SeatsResponseDTO();
//        Seats seat = new Seats();
//
//        if(seats.isEmpty()){
//            integerId = Long.parseLong(StringId);
//            seat = seatsService.getReferenceById(integerId);
//            result = seats.convertToResponse();
//        } else {
//            seatsget = seats.get();
//            result = seatsget.convertToResponse();
//        }
//
//        model.addAttribute("seat_getId", result);
//        return "Seats_GetById";



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


    //DELETE
    @GetMapping("/MVC/Seats/delete/{id}")
    public String showDeleteSeats(@PathVariable("id") Long id) {
//        Seats seats = seatsService.getSeatsById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid seats Id:" + id));
        this.seatsService.deleteseat(id);
        return "redirect:/MVC/Seats";
    }
}
