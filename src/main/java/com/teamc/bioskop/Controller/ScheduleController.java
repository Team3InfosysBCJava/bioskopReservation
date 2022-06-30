package com.teamc.bioskop.Controller;

import com.teamc.bioskop.DTO.ScheduleResponseFilmSeatDTO;
import com.teamc.bioskop.DTO.ScheduleResponseNameLikeDTO;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.*;
import com.teamc.bioskop.Response.ResponseHandler;
import com.teamc.bioskop.Service.*;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teamC/v1")
@AllArgsConstructor
public class ScheduleController {
    private static final Logger logger = LogManager.getLogger(ScheduleController.class);
    private static final String Line = "============================================================";

    private ScheduleService scheduleService;
    private FilmsService filmsService;
    private SeatsService seatsService;

    /**
     *Get all of data from schedules table
     */
   @GetMapping("/schedule")
    public ResponseEntity<Object> ScheduleList(){
       try {
           List<Schedule> result = scheduleService.getAll();
           List<ScheduleResponseFilmSeatDTO> scheduleMaps = new ArrayList<>();
           logger.info(Line + "Logger Start Find All Schedule" + Line);
           for (Schedule dataResult:result){
               logger.info("================================");
               logger.info("Schedule id :"+dataResult.getScheduleId());
               logger.info("Film :"+dataResult.getFilms());
;              logger.info("Seats :"+dataResult.getSeats());
               logger.info("================================");
               ScheduleResponseFilmSeatDTO scheduleDTO = dataResult.convertToResponseFilmsSeat();
               scheduleMaps.add(scheduleDTO);
           }
           logger.info(Line + "Logger End Find All Schedule" + Line);
           return ResponseHandler.generateResponse("Successfully  getAll data!", HttpStatus.OK, scheduleMaps);
       }
       catch (ResourceNotFoundException e) {
           logger.error("------------------------------------");
           logger.error(e.getMessage());
           logger.error("------------------------------------");
        return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Tabel has no Value");
       }
   }

    /**
     *  create new schedule into schedules table
     *  throws ResourceNotFoundException if bad request happened
     */
    @PostMapping("/schedule")
    public ResponseEntity<Object>createSchedule(@RequestBody Schedule schedule){
            try{
                Films films = filmsService.getReferenceById(schedule.getFilms().getFilmId());
                Seats seats = seatsService.getReferenceById(schedule.getSeats().getSeatId());
                schedule.setFilms(films);
                schedule.setSeats(seats);
                Schedule scheduleCreate = scheduleService.createSchedule(schedule);
                ScheduleResponseFilmSeatDTO result = scheduleCreate.convertToResponseFilmsSeat();
                logger.info(Line + "Logger Start Create New Schedule" + Line);
                logger.info(schedule);
                logger.info(Line + " Logger Stop Create New Schedule" + Line);
                return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.CREATED,result);
        }
            catch (Exception e){
                logger.error("------------------------------------");
                logger.error(e.getMessage());
                logger.error("------------------------------------");
                return ResponseHandler.generateResponse(e.getMessage(),HttpStatus.BAD_REQUEST, "Bad Request");
       }
    }

    /**
     *Get Schedule by Schedule id
     * throws ResourceNotFoundException if data is not found
     */
    @GetMapping("/schedule/{id}")
    public ResponseEntity<Object> getscheduleById(@PathVariable Integer id){
       try {
           Optional<Schedule> schedule = scheduleService.getScheduleById(id);
           Schedule scheduleget = schedule.get();
           ScheduleResponseFilmSeatDTO result = scheduleget.convertToResponseFilmsSeat();
           logger.info(Line + " Logger Start Find Schedule ById " + Line);
           logger.info("GetById");
           logger.info(result);
           logger.info(Line + " Logger End Find Schedule By Id" + Line);
           return ResponseHandler.generateResponse("Successfully retrieved data!", HttpStatus.OK, result);
       } catch (ResourceNotFoundException e) {
           logger.error("------------------------------------");
           logger.error(e.getMessage());
           logger.error("------------------------------------");
         return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found!");
       }
    }

    /**
     * update schedule
     * throws ResourceNotFoundException if data not found
     */
    @PutMapping("/schedule/{id}")
        public ResponseEntity<Object> updateSchedule(@PathVariable Integer id,@RequestBody Schedule scheduleDetails){
       try {
       Optional<Schedule> scheduleByid = scheduleService.getScheduleById(id);
       Schedule schedule = scheduleByid.get();
//        Schedule updateSchedule = scheduleService.getReferenceById(scheduleDetails.getScheduleId());
       Films films = filmsService.getReferenceById(scheduleDetails.getFilms().getFilmId());
       Seats seats = seatsService.getReferenceById(scheduleDetails.getSeats().getSeatId());
       schedule.setSeats(seats);
       schedule.setFilms(films);
       schedule.setPrice(scheduleDetails.getPrice());
       schedule.setDateShow(scheduleDetails.getDateShow());
       schedule.setShowStart(scheduleDetails.getShowStart());
       schedule.setShowEnd(scheduleDetails.getShowEnd());
       Schedule scheduleUpdate = scheduleService.updateSchedule(schedule);
       ScheduleResponseFilmSeatDTO result = scheduleUpdate.convertToResponseFilmsSeat();
        logger.info(Line + " Logger Start Updated Data" + Line);
        logger.info("Update");
        logger.info(result);
        logger.info(Line + "Logger Finish Updated Data" + Line);
        return ResponseHandler.generateResponse("Data Updated!", HttpStatus.CREATED, result);
    }
       catch (Exception e){
           logger.error("------------------------------------");
           logger.error(e.getMessage());
           logger.error("------------------------------------");
         return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");    }
       }


    /**
     * delete schedule by id
     * throws ResourceNotFoundException if data is not found
     */
    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Object> delete (@PathVariable Integer id){
       try {
           scheduleService.deleteScheduleById(id);
           Map<String, Boolean> response = new HashMap<>();
           response.put("deleted", Boolean.TRUE);
           logger.info(Line + " Logger Deleted Somedata" + Line);
           logger.info(response);
           logger.info(Line + " Logger Deleted Done!" + Line);
           return ResponseHandler.generateResponse("Deleted! ", HttpStatus.OK, response);
    }
       catch (ResourceNotFoundException e){
           logger.error("------------------------------------");
           logger.error(e.getMessage());
           logger.error("------------------------------------");
//           logger.info(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found"));
           return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found");
       }
       }

    /**
     *  custom Challenge 4 slide 8 nomor 2.1
     *  create schedule from one of film name at schedules table
     *  throws ResourceNotFoundException if film name is not found
     */
    @PostMapping("/schedule/byfilmname")
    public ResponseEntity<Object> findScheduleByFilmName(@RequestBody Films film){
        try {
            List<Schedule> scheduleByFilmName = scheduleService.getScheduleByFilmName(film.getName());
            List<ScheduleResponseNameLikeDTO> results = scheduleByFilmName.stream()
                    .map(Schedule::convertToResponseNameLike)
                    .collect(Collectors.toList());
            logger.info(Line + " Logger Start Create Schedule" + Line);
            logger.info(results);
            logger.info(Line + " Logger Finish Create Schedule" + Line);
            return ResponseHandler.generateResponse("Successfully added data!", HttpStatus.CREATED, results);
        }
        catch (Exception e){
            logger.error("------------------------------------");
            logger.error(e.getMessage());
            logger.error("------------------------------------");
//            logger.info(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found"));
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");
        }
        }


    /**
     * custom Challange 4 slide 8 nomor 2.2
     * create schedule from one of specific film name at schedules table
     *throws ResourceNotFoundException if film name is not found
     */
    @PostMapping("/schedule/byfilmnameLike")
    public ResponseEntity<Object> findScheduleByFilmNameLike(@RequestBody Films film){
       try {
           List<Schedule> scheduleByFilmName = scheduleService.getScheduleByFilmNameLike(film.getName());
           List<ScheduleResponseNameLikeDTO> results = scheduleByFilmName.stream()
                   .map(Schedule::convertToResponseNameLike)
                   .collect(Collectors.toList());
        logger.info(Line + " Logger Start Create Schedule By Film Name" + Line);
        logger.info(results);
        logger.info(Line + " Logger Finish Create Schedule By Film Name" + Line);
        return ResponseHandler.generateResponse("Successfully added data ! ", HttpStatus.OK, results);
    }
       catch (Exception e){
           logger.error("------------------------------------");
           logger.error(e.getMessage());
           logger.error("------------------------------------");
//           logger.info(ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found"));
           return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data Not Found");
       }
       }


}