package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.FilmsResponseDTO;
import com.teamc.bioskop.DTO.ResponseHandler;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Service.FilmsService;
import com.teamc.bioskop.Service.FilmsServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.*;

@AllArgsConstructor
@Controller
public class FilmsContMVC {

    private final FilmsService filmsService;
    private final FilmsServiceImpl filmsServiceImpl;
    private static final Logger logger = LogManager.getLogger(FilmsContMVC.class);
    private static final String Line = "====================";

    //GET ALL
    @GetMapping("/MVC/Film")
    public String showFilmList(Model model, @Param("keyword") String keyword, @Param("page") String page) {
        try {
            Integer pageNumber = null;

            //check null pointer
            if (page != null) {
                pageNumber = filmsServiceImpl.pageUpdate(page);
            }

            //Pagination
            Page<Films> results = filmsService.search(keyword, pageNumber);

            List<Map<String, Object>> maps = new ArrayList<>();
            logger.info(Line + " Logger Start Get All Films " + Line);
            for (Films dataresults : results) {
                Map<String, Object> films = new HashMap<>();
                logger.info(Line);
                logger.info("Film id : " + dataresults.getFilmId() +
                        ", Film Name : " + dataresults.getName() +
                        ", Status Playing : " + dataresults.getIsPlaying());
                logger.info("Line");
                films.put("Id :", dataresults.getFilmId());
                films.put("Film Name :", dataresults.getName());
                films.put("Status :", dataresults.getIsPlaying());
                maps.add(films);
            }
            logger.info(Line + " Logger End Get All Films " + Line);

            //alias masuk ke file html
            model.addAttribute("films", results);
            model.addAttribute("keyword", keyword);
            model.addAttribute("film_add", new Films());
            return "Films_GetAll"; //ngambil file html
        }catch(Throwable e){
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.NOT_FOUND);
            ResponseHandler.generateResponse(e.getMessage(),HttpStatus.NOT_FOUND, "Table Has No Value!");
            return "error_page";
        }
    }

    //GET BY ID
    @GetMapping("/MVC/Film/Id")
    public String searchbyID(Model model, @Param("id") Long id, @Param("page") String page) {
        try {

            Integer pageNumber = null;

            if (page != null) {
                pageNumber = filmsServiceImpl.pageUpdate(page);
            }

            //Pagination
            Page<Films> results = filmsServiceImpl.getFilmById(id, pageNumber);
//           Films films = filmsService.getReferenceById(id);

            List<Map<String, Object>> maps = new ArrayList<>();

            logger.info(Line + " Logger Start Search By Id " + Line);
            for (Films dataresults : results) {
                Map<String, Object> films = new HashMap<>();
                logger.info(Line);
                logger.info("Film id : " + dataresults.getFilmId() +
                        ", Film Name : " + dataresults.getName() +
                        ", Status Playing : " + dataresults.getIsPlaying());
                logger.info("Line");
                films.put("Id :", dataresults.getFilmId());
                films.put("Film Name :", dataresults.getName());
                films.put("Status :", dataresults.getIsPlaying());
                maps.add(films);
            }
            logger.info(Line + " Logger End Get All Films " + Line);

            model.addAttribute("films", results);
            model.addAttribute("id", id);
            model.addAttribute("film_add", new Films());
            ResponseHandler.generateResponse("Success Search By Id", HttpStatus.OK, maps);
            return "Films_GetAll";
        } catch (Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.NOT_FOUND);
            ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value");
            return "error_page";
        }
    }

    @GetMapping("/MVC/Film/{id}")
    public String showFilmById(@PathVariable("id") Long filmId, String stringId, Model model) {

        Optional<Films> films = filmsService.findbyId(filmId);
        Long integerId = null;
        Films filmsget = new Films();
        FilmsResponseDTO result = new FilmsResponseDTO();
        Films film = new Films();

        if(films.isEmpty()){
            integerId = Long.parseLong(stringId);
            film = filmsService.getReferenceById(integerId);
            result = film.convertToResponse();
        } else {
            filmsget = films.get();
            result = filmsget.convertToResponse();
        }

        model.addAttribute("film_getId", result);
        return "Films_GetById";
    }

    //CREATE
    @GetMapping("/MVC/Film/new-film")
    public String showFilmForm(Films films) {
        return "Films_AddNew";
    }

    @PostMapping("/MVC/Film/add-film")
    public String showAddFilm(@Valid Films films, BindingResult result, Model model){
        try{
            Films results = filmsServiceImpl.createFilm(films);

            //logger
            logger.info(Line + " Logger Start Create " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Create " + Line);

            ResponseHandler.generateResponse("Success Created",HttpStatus.CREATED, results);
            return "redirect:/MVC/Film";
        } catch (Exception e) {
            logger.info(Line + " Logger Start Create " + Line);
            logger.info(e.getMessage());
            logger.info(Line + " Logger End Create " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.BAD_REQUEST);
            ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, "Bad Request");
            return "error_page";
        }
    }

    //DELETE
    @GetMapping("/MVC/Film/delete/{id}")
    public String showDeleteFilm(@PathVariable("id") Long id, Model model) {
        try {
            Films results = filmsServiceImpl.findbyId(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid film id: " + id));
        filmsServiceImpl.deleteFilmById(id);

            logger.info(Line + " Logger Start Delete " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Create " + Line);

            ResponseHandler.generateResponse("Success Created", HttpStatus.OK, results);
            return "redirect:/MVC/Film";
        } catch(Exception e) {
            logger.error(Line + " Logger Start Error " + Line);
            logger.error(e.getMessage());
            logger.error(Line + " Logger End Error " + Line);
            model.addAttribute("error", e.getMessage());
            model.addAttribute("errorStatus", HttpStatus.NOT_FOUND);
            ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, "Table has no value");
            return "error_page";
        }
    }

    //UPDATE
    @GetMapping("/MVC/Film/update/{id}")
    public String showEditFilmForm(@PathVariable("id") Long filmId, Model model){
        Optional<Films> films = filmsService.findbyId(filmId);
        Films filmsget = films.get();
        model.addAttribute("films", filmsget);
        return "Films_Update";
    }

    @PostMapping("/MVC/Film/update-film/{id}")
    public String showUpdateFilms(@PathVariable("id") Long filmId, @Valid Films films, BindingResult result, Model model) {
        try {
            films.setFilmId(filmId);
            Films results = filmsServiceImpl.updateFilm(films);

            //logger
            logger.info(Line + " Logger Start Create " + Line);
            logger.info(results);
            logger.info(Line + " Logger End Create " + Line);

            ResponseHandler.generateResponse("Success Created", HttpStatus.OK, results);

            return "redirect:/MVC/Film";
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

}
