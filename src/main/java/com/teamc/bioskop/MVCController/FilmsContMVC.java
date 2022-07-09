package com.teamc.bioskop.MVCController;

import com.teamc.bioskop.DTO.FilmsResponseDTO;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Service.FilmsService;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class FilmsContMVC {

    private final FilmsService filmsService;

    //GET ALL
    @GetMapping("/MVC/Film")
    public String showFilmList(Model model, @Param("keyword") String keyword) {
        List<Films> films = filmsService.search(keyword);

        //alias masuk ke file html
        model.addAttribute("films", films);
        model.addAttribute("keyword",keyword);
        model.addAttribute("film_add", new Films());
        return "Films_GetAll"; //ngambil file html
    }

    //GET BY ID
    @GetMapping("/MVC/Film/Id")
    public String searchbyID(Model model, @Param("id") Long id){
       Films films = filmsService.getReferenceById(id);
    //    FilmsResponseDTO result = films.convertToResponse();
        model.addAttribute("films", films);
        model.addAttribute("id",id);
        return "Films_GetAll";
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
        if (result.hasErrors()) {
            return "Films_AddNew";
        }
        // if (films.getIsPlaying().equals(null)) {
        //     films.setIsPlaying(0);
        // }
        // else {
        //     films.setIsPlaying(1);
        // }

        filmsService.createFilm(films);
        return "redirect:/MVC/Film";
    }

    //DELETE
    @GetMapping("/MVC/Film/delete/{id}")
    public String showDeleteFilm(@PathVariable("id") Long filmId) {
        this.filmsService.deleteFilmById(filmId);
        return "redirect:/MVC/Film";
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
    public String showUpdateFilms(@PathVariable("id") Long filmId, @Valid Films films, BindingResult result, Model model){
        if (result.hasErrors()) {
            films.setFilmId(filmId);
            return "Films_Update";
        }

        films.setFilmId(filmId);
        filmsService.updateFilm(films);
        return "redirect:/MVC/Film";
    }

}
