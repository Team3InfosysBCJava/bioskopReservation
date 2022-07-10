package com.teamc.bioskop.Controller;

import com.teamc.bioskop.DTO.FilmsRequestDTO;
import com.teamc.bioskop.DTO.FilmsResponseDTO;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Response.ResponseHandler;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Service.FilmsService;
import lombok.AllArgsConstructor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/teamC/v1/DTO")
@AllArgsConstructor
public class FilmsControllerDTO {

    private static final Logger logger = LogManager.getLogger(FilmsControllerDTO.class);
    private static final String Line = "======================";
    private FilmsService filmsService;

    /**
     * GET ALL FILM
     */
    @GetMapping("/films")
    public ResponseEntity<Object> findAllFilms() {
        try {
            List<Films> films = filmsService.findAllFilms();
            List<FilmsResponseDTO> result = new ArrayList<>();
            logger.info(Line + "Logger Start Find All Films" + Line);
            for (Films dataResult:films) {
                FilmsResponseDTO filmsResponseDTO = dataResult.convertToResponse();
                result.add(filmsResponseDTO);
                logger.info("================================");
                logger.info("code :"+dataResult.getFilmId() + " || " + "title :"+dataResult.getName()+" || "+"status :"+dataResult.getIsPlaying());
            }
            logger.info(Line + "Logger End Find All Films" + Line);
            return ResponseHandler.generateResponse("Succes All", HttpStatus.OK,result);
        } catch (ResourceNotFoundException e) {
            logger.error(Line + "Logger Start" + Line);
            logger.error(e.getMessage() + " || " + "Code Status:" + HttpStatus.NOT_FOUND );
            logger.error(Line + "Logger End" + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value");
        }
    }

    /**
     * GET FILM By ID
     */
    @GetMapping("/films/{filmId}")
    public ResponseEntity<Object> getfilmById(@PathVariable Long filmId) {
        try {
            Optional<Films> films = filmsService.findbyId(filmId);
            Films filmget = films.get();
            FilmsResponseDTO filmsResponseDTO = filmget.convertToResponse();
//            filmget.add(filmsResponseDTO);
            logger.info(Line + "Logger Start Find Films by Id" + Line);
            logger.info("Get Film " + filmget);
            logger.info(Line + "Logger End Find Films by Id" + Line);
            return ResponseHandler.generateResponse("Succes Get", HttpStatus.OK, filmget);
        } catch (ResourceNotFoundException e) {
            logger.error(Line + "Logger Start" + Line);
            logger.error("Data not found" + " || " + e.getMessage() + " || " + "Code Status:" + HttpStatus.NOT_FOUND );
            logger.error(Line + "Logger End" + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found");
        }
    }

    /**
     * UPDATE FILM By ID
     */
    @PutMapping("/films/{filmId}")
    public ResponseEntity<Object> filmsupdate(@PathVariable Long filmId, @RequestBody FilmsRequestDTO filmsRequestDTO) {
        try {
            Films films = filmsRequestDTO.convertToEntity();
            films.setFilmId(filmId);
            Films filmsUpdate = filmsService.updateFilm(films);
            logger.info(Line + "Logger Start Update Films by Id" + Line);
            logger.info("Film updated " + filmsUpdate);
            logger.info(Line + "Logger End Update Films by Id" + Line);
            return ResponseHandler.generateResponse("Succes Update", HttpStatus.CREATED, filmsUpdate);
        } catch (Exception e) {
            logger.error(Line + "Logger Start" + Line);
            logger.error("Data not found" + " || " + e.getMessage() + " || " + "Code Status:" + HttpStatus.NOT_FOUND );
            logger.error(Line + "Logger End" + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found");
        }
    }

    /**
     * DELETE FILM By ID
     */

    @DeleteMapping("/films/{filmId}")
    public ResponseEntity<Object> deleteFilm(@PathVariable Long filmId) {
        try {
            filmsService.deleteFilmById(filmId);
            Map<String, Boolean> response = new HashMap<>();
            response.put("deleted", Boolean.TRUE);
            logger.info(Line + "Logger Start Delete Films by Id" + Line);
            logger.info("Film deleted " + response);
            logger.info(Line + "Logger End Delete Films by Id" + Line);
            return ResponseHandler.generateResponse("Succes Delete", HttpStatus.OK, response);
        } catch (ResourceNotFoundException e) {
            logger.error(Line + "Logger Start" + Line);
            logger.error("Failed delete data" + " || " + e.getMessage() + " || " + "Code Status:" + HttpStatus.NOT_FOUND );
            logger.error(Line + "Logger End" + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Failed delete data");
        }
    }

    /**
     * CREATE FILM
     */
    @PostMapping("/films")
    public ResponseEntity<Object> createFilm(@RequestBody FilmsRequestDTO filmRequestDTO) {
        try {
            Films filmsCreate = filmRequestDTO.convertToEntity();
            filmsService.createFilm(filmsCreate);
            logger.info(Line + "Logger Start Create Films" + Line);
            logger.info("Film Created " + filmsCreate);
            logger.info(Line + "Logger End Create Films" + Line);
            return ResponseHandler.generateResponse("Succes Create", HttpStatus.CREATED, filmsCreate);
        } catch (Exception e) {
            logger.error(Line + "Logger Start" + Line);
            logger.error("Failed Create data" + " || " + e.getMessage() + " || " + "Code Status:" + HttpStatus.BAD_REQUEST );
            logger.error(Line + "Logger End" + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Failed create data");
        }
    }

    /**
     * FIND FILM By IsPlaying
     */
    @PostMapping("/fimsDTO/{isPlaying}")
    public ResponseEntity<Object> findFilmIsPlaying(@PathVariable Boolean isPlaying) {
        try {
            List<Films> filmIsPlaying = filmsService.getByIsPlaying(isPlaying);
            logger.info(Line + "Logger Start Find Films is Playing" + Line);
            logger.info("Get Film " + filmIsPlaying);
            logger.info(Line + "Logger End Find Films" + Line);
            return ResponseHandler.generateResponse("Find Film Succes", HttpStatus.OK, filmIsPlaying);
        } catch (Exception e) {
            logger.error(Line + "Logger Start" + Line);
            logger.error("Data not found" + " || " + e.getMessage() + " || " + "Code Status:" + HttpStatus.NOT_FOUND );
            logger.error(Line + "Logger End" + Line);
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Data not found");
        }
    }
}