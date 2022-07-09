package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.ScheduleResponseDTO;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Repository.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import com.teamc.bioskop.Service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ScheduleMVCController {
    private final ScheduleService scheduleService;
    private final ScheduleRepository scheduleRepository;

    //HOMEPAGE
    @GetMapping("/MVC")
    public String showHomepage(){
        return "Homepage";
    }

    //SEARCH BY FILM NAME
    @PostMapping("/MVC/schedules/search-by-film")
    public String searchFilm(Films film, Model model, String name) {
        if(name!=null) {
            Page<Schedule> schedules = scheduleService.search(film.getName(), null);
            //pake stream
            // List<ScheduleResponseDTO> results = schedules.stream()
            //         .map(Schedule::convertToResponse)
            //         .collect(Collectors.toList());
            model.addAttribute("schedule_entry", schedules);
        }else {
            List<Schedule> schedules = scheduleService.getAll();
            // List<ScheduleResponseDTO> schedulesMaps = new ArrayList<>();
            //manual for each
            // for (Schedule data : schedules){
            //     ScheduleResponseDTO scheduleDTO = data.convertToResponse();
            //     schedulesMaps.add(scheduleDTO);
            // }
            model.addAttribute("schedule_entry", schedules);
        }
        return "Schedules_Index";
    }

    //CREATE
    @GetMapping("/MVC/new-schedule")
    public String showScheduleForm(Schedule schedule) {
        return "Schedules_AddNew";
    }

    @PostMapping("/MVC/schedules/add-schedule")
    public String showAddSchedule(@Valid Schedule schedule, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "Schedules_AddNew";
        }

        scheduleService.createSchedule(schedule);
        return "redirect:/MVC/schedules";
    }


    //GET ALL
    @GetMapping("/MVC/schedules")
    public String search(Model model, @Param("keyword") String keyword, @Param("page") String page){

        //Pagination
        Integer pageNumber = Integer.parseInt(page);
        Page<Schedule> result = scheduleService.search(keyword, pageNumber);

        //Return Output
        model.addAttribute("schedule_entry", result);
        model.addAttribute("keyword",keyword);
        model.addAttribute("schedule",new Schedule());
        return "Schedules_Index";
    }


    //GET BY ID
    @GetMapping("/MVC/schedules/{id}")
    public String showScheduleById(@PathVariable("id") Integer id, Model model){

            Optional<Schedule> schedule = scheduleService.getScheduleById(id);
            Schedule scheduleget = schedule.get();
            ScheduleResponseDTO result = scheduleget.convertToResponse();
            model.addAttribute("schedule_entry", result);
            return "Schedules_GetById";
    }


    //UPDATE
    @GetMapping("/MVC/schedules/update/{id}")
    public String showEditScheduleForm(@PathVariable("id") Integer id, Model model){
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        Schedule scheduleget = schedule.get();
        model.addAttribute("schedule", scheduleget);
        return "Schedules_Update";
    }

    @PostMapping("/MVC/schedules/update-schedule/{id}")
    public String showUpdateSchedule(@PathVariable("id") Integer id, @Valid Schedule schedule, BindingResult result, Model model){
        if (result.hasErrors()) {
            schedule.setScheduleId(id);
            return "Schedules_Update";
        }

        schedule.setScheduleId(id);
        scheduleService.updateSchedule(schedule);
        return "redirect:/MVC/schedules";
    }


    //DELETE BY ID
    @GetMapping("/MVC/schedules/delete/{id}")
    public String showDeleteSchedule(@PathVariable("id") Integer id, Model model) {
//        Schedule schedule = scheduleService.getScheduleById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid schedule Id:" + id));
        scheduleService.deleteScheduleById(id);
        return "redirect:/MVC/schedules";
    }
}
