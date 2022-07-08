package com.teamc.bioskop.Service;

import com.teamc.bioskop.Model.Films;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public interface FilmsService {
   List<Films> findAllFilms();
   Optional<Films> findbyId(Long filmId);
   Films createFilm(Films films);
   Films updateFilm(Films films);
   void deleteFilmById(Long id);
   List<Films> getByIsPlaying(Integer isPlaying);
   Films getReferenceById (Long id);
   public List<Films> search(String keyword);


}