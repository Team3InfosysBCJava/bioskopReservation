package com.teamc.bioskop.MVCController;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.teamc.bioskop.DTO.BookingResponseDTO;
import com.teamc.bioskop.DTO.ResponseHandler;
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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
@AllArgsConstructor
@Controller
public class ReservationContMVC {

    private final BookingService bookingService;
    private static final Logger logger = LogManager.getLogger(ReservationContMVC.class);
    private static final String Line = "====================";

    /**
     * Search anything , default get all
     */
    @GetMapping("/MVC/Reservations")
    public String search(Model model, @Param("keyword") String keyword, @Param("page") String page){
        try {
            //Pagination
            Page<Reservation> results = bookingService.search(keyword, page);

            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info(Line + " Logger Start Get All Films " + Line);
            for (Reservation dataresults : results) {
                Map<String, Object> reservation = new HashMap<>();
                logger.info(Line);
                logger.info("Reservation id : " + dataresults.getReservationId() +
                        ", Title Film : " + dataresults.getSchedule().getFilms().getName() +
                        ", Status Playing : " + dataresults.getSchedule().getFilms().getIsPlaying() +
                        ", Studio : " + dataresults.getSchedule().getSeats().getStudioName() +
                        ", Seat Number : " + dataresults.getSchedule().getSeats().getSeatNumber() +
                        ", Status Seat : " + dataresults.getSchedule().getSeats().getIsAvailable() +
                        ", Price : " + dataresults.getSchedule().getPrice() +
                        ", Date Show : " + dataresults.getSchedule().getDateShow() +
                        ", Show Start : " + dataresults.getSchedule().getShowStart() +
                        ", Show End : " + dataresults.getSchedule().getShowEnd());
                logger.info(Line);
                reservation.put("Id :", dataresults.getReservationId());
                reservation.put("Title :", dataresults.getSchedule().getFilms().getName());
                reservation.put("Studio : ", dataresults.getSchedule().getSeats().getStudioName());
                reservation.put(" Status Seat : ", dataresults.getSchedule().getSeats().getIsAvailable());
                reservation.put("Price : ", dataresults.getSchedule().getPrice());
                reservation.put("Show : ", dataresults.getSchedule().getDateShow());
                reservation.put("Start : ", dataresults.getSchedule().getShowStart());
                reservation.put("Show End : ", dataresults.getSchedule().getShowEnd());
                maps.add(reservation);
            }
            logger.info(Line + " Logger End Get All Films " + Line);
            model.addAttribute("Reservation_entry", results);
            model.addAttribute("keyword", keyword);
            model.addAttribute("reservation", new Reservation());
            ResponseHandler.generateResponse("Success Get All", HttpStatus.OK, maps);
            return "Index_Reservation";

        }catch(Throwable e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus",HttpStatus.NOT_FOUND);
            ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
            return "error_page";
        }
    }

    /**
     * Search by ID *experiment
     */
    @GetMapping("/MVC/Reservation/Id")
    public String search(Model model, @Param("id") long id, @Param("page") String page){
        try{
        //Pagination
        Page<Reservation> results = bookingService.getBookingId(id, page);
        //Logger
        List<Map<String, Object>> maps = new ArrayList<>();
        logger.info(Line + " Logger Start Search By ID " + Line);
        for (Reservation dataresults : results) {
                Map<String, Object> reservation = new HashMap<>();
                logger.info(Line);
                logger.info("Reservation id : " + dataresults.getReservationId() +
                        ", Title Film : " + dataresults.getSchedule().getFilms().getName() +
                        ", Status Playing : " + dataresults.getSchedule().getFilms().getIsPlaying() +
                        ", Studio : " + dataresults.getSchedule().getSeats().getStudioName() +
                        ", Seat Number : " + dataresults.getSchedule().getSeats().getSeatNumber() +
                        ", Status Seat : " + dataresults.getSchedule().getSeats().getIsAvailable() +
                        ", Price : " + dataresults.getSchedule().getPrice() +
                        ", Date Show : " + dataresults.getSchedule().getDateShow() +
                        ", Show Start : " + dataresults.getSchedule().getShowStart() +
                        ", Show End : " + dataresults.getSchedule().getShowEnd());
                logger.info(Line);
                reservation.put("Id :", dataresults.getReservationId());
                reservation.put("Title :", dataresults.getSchedule().getFilms().getName());
                reservation.put("Studio : ", dataresults.getSchedule().getSeats().getStudioName());
                reservation.put(" Status Seat : ", dataresults.getSchedule().getSeats().getIsAvailable());
                reservation.put("Price : ", dataresults.getSchedule().getPrice());
                reservation.put("Show : ", dataresults.getSchedule().getDateShow());
                reservation.put("Start : ", dataresults.getSchedule().getShowStart());
                reservation.put("Show End : ", dataresults.getSchedule().getShowEnd());
                maps.add(reservation);
            }
            logger.info(Line + " Logger End Search By ID " + Line);

        model.addAttribute("Reservation_entry", results);
        model.addAttribute("id",id);
        model.addAttribute("reservation",new Reservation());
        ResponseHandler.generateResponse("Success Search By Id", HttpStatus.OK, maps);
        return "Index_Reservation";
        }

        catch(Throwable e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus",HttpStatus.NOT_FOUND);
            ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
            return "error_page";
        }
    }

    /**
     * Search filmname *experimen
     */
    @PostMapping("/MVC/Reservation/searchFilm")
    public String searchFilm(Films film, Model model, String name,@Param("page") String page) {
        try {
            Page<Reservation> results = bookingService.getBookingFilm(film.getName(), page);
            model.addAttribute("Reservation_entry", results);
            model.addAttribute("reservation", new Reservation());

                //logger
            logger.info(Line + " Logger Start Search by Filmname " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Search by Filmname " + Line);

            ResponseHandler.generateResponse("Success Search By Filmname", HttpStatus.OK, results);
            return "Index_Reservation";

        }catch (Exception e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.BAD_REQUEST);
            ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");
            return "error_page";
        }
    }

    /**
     * Create
     */
    @GetMapping("/MVC/Reservation/new")
    public String showRerservationForm(Reservation reservation){

        return "Add_Reservation";
    }

    @PostMapping("/MVC/Reservation/add")
    public String showAddReservation(@Valid Reservation reservation, BindingResult result, Model model) {
        try {
            Reservation results = bookingService.createBooking(reservation);

            //logger
            logger.info(Line + " Logger Start Create " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Create " + Line);

            ResponseHandler.generateResponse("Success Created", HttpStatus.CREATED, results);
            return "redirect:/MVC/Reservations";
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.BAD_REQUEST);
            ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");
            return "error_page";
        }
    }

    /**
     * Update
      */
    @PostMapping("/MVC/Reservation/update-reservation/{id}")
    public String showUpdateReservation(@PathVariable("id") Long id, @Valid Reservation reservation, BindingResult result, Model model){
        try {
            reservation.setReservationId(id);
            Reservation results =bookingService.updateBooking(reservation);
            //logger
            logger.info(Line + " Logger Start Create " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Create " + Line);

            ResponseHandler.generateResponse("Success Created", HttpStatus.OK, results);

            return "redirect:/MVC/Reservations";
        }catch(Exception e){

            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.BAD_REQUEST);
            ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");
            return "error_page";
        }
    }

    /**
     * Delete by ID
     */
    @GetMapping("/MVC/Reservation/delete/{id}")
    public String showDeleteReservationById(@PathVariable("id") Long id, Model model){
        try{
        Reservation results = bookingService.getBookingById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid reservation Id:" + id));
        bookingService.deleteSBookingById(id);

            logger.info(Line + " Logger Start Delete " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Create " + Line);

            ResponseHandler.generateResponse("Success Created", HttpStatus.OK, results);
            return "redirect:/MVC/Reservations";

        }catch(Exception e){
        logger.error(Line + " Logger Start Error " + Line);
        logger.error(e.getMessage());
        logger.error(Line + " Logger End Error " + Line);
        model.addAttribute("error", e.getMessage());
        model.addAttribute("errorStatus",HttpStatus.NOT_FOUND);
        ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND,"Table has no value");
        return "error_page";
    }
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                Model model) {
        int pageSize = 5;

        Page<Reservation> page = bookingService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Reservation> listEmployees = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listEmployees", listEmployees);
        return "index";
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