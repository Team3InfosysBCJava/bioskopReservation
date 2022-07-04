package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.ScheduleResponseDTO;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Response.ResponseHandler;
import com.teamc.bioskop.Service.ScheduleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ScheduleMVCController {
    private final ScheduleService scheduleService;

    @GetMapping("/MVC/schedules")
    public String showScheduleList(Model model){
        List<Schedule> result = scheduleService.getAll();
        model.addAttribute("schedule_entry", result);
        return "Schedules_GetAll";

    }

    @GetMapping("/MVC/schedules/{id}")
    public String showScheduleById(@PathVariable("id") Integer id, Model model){

            Optional<Schedule> schedule = scheduleService.getScheduleById(id);
            Schedule scheduleget = schedule.get();
            ScheduleResponseDTO result = scheduleget.convertToResponse();
            model.addAttribute("schedule_entry", result);
            return "Schedules_GetById";
    }
}
