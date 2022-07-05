package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.ScheduleResponseDTO;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Response.ResponseHandler;
import com.teamc.bioskop.Service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    //HOMEPAGE
    @GetMapping("/MVC")
    public String showHomepage(){
        return "Homepage";
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
    public String showScheduleList(Model model){
        List<Schedule> result = scheduleService.getAll();
        model.addAttribute("schedule_entry", result);
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