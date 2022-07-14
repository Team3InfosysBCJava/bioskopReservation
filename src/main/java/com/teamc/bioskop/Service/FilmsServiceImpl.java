package com.teamc.bioskop.Service;

import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Repository.FilmsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class FilmsServiceImpl implements FilmsService {

    private final FilmsRepository filmsRepository;
    public static Integer currentPage;

    //Get All
    @Override
    public List<Films> findAllFilms() {
        List<Films> optionalFilms = filmsRepository.findAll();
        if(optionalFilms.isEmpty()){
            throw new ResourceNotFoundException("table films have not value");
        }
        return filmsRepository.findAll();
    }

    //Get by id
    @Override
    public Optional<Films> findbyId(Long filmId){
        Optional<Films> optionalFilms = filmsRepository.findById(filmId);
        if(optionalFilms.isEmpty()){
            throw new ResourceNotFoundException("Films not exist with id : " + filmId);
        }
        return filmsRepository.findById(filmId);
    }


    //Post
    @Override
    public Films createFilm(Films films) {

        return filmsRepository.save(films);
    }

    //Update
    @Override
    public Films getReferenceById (Long id) {
        return this.filmsRepository.getReferenceById(id);
    }


    @Override
    public Films updateFilm(Films films) {
        return filmsRepository.save(films);
    }

    //Delete
    @Override
    public void deleteFilmById(Long filmId){
        Optional<Films> optionalFilms = filmsRepository.findById(filmId);
        if(optionalFilms.isEmpty()){
            throw new ResourceNotFoundException("Films not exist with id : " + filmId);
        }
        filmsRepository.deleteAllById(Collections.singleton(filmId));
    }

    //Custom select
    public  List<Films> getByIsPlaying(Boolean isPlaying){
        List<Films> optionalFilms = filmsRepository.getFilmByIsPlaying(isPlaying);
        if (optionalFilms.isEmpty()){
            throw new ResourceNotFoundException("Films not exist with status available : " + isPlaying);
        }

        return this.filmsRepository.getFilmByIsPlaying(isPlaying);
    }

    public List<Films> search(String keyword){
        if (keyword != null){
            return filmsRepository.search(keyword);
        }

        return filmsRepository.findAll();
    }

    @Override
    public Page<Films> search(String keyword, Integer page){
        if (keyword != null){
            return filmsRepository.Search(keyword, null);
        } else if (page == null){
            return filmsRepository.findAll(PageRequest.of(0, 10,Sort.by("filmId")));
        } else {
            return filmsRepository.findAll(PageRequest.of(page, 10,Sort.by("filmId")));
        }
    }

    @Override
    public Page<Films> getFilmById(Long id, Integer page){
        if (id != null){
            return filmsRepository.getFilmId(id, null);
        } else if (page == null){
            return filmsRepository.findAll(PageRequest.of(0, 10,Sort.by("filmId")));
        } else {
            return filmsRepository.findAll(PageRequest.of(page, 10,Sort.by("filmId")));
        }
    }


    public Integer pageUpdate(String page) {

        //container
        Integer pageNumber = null;

        //check params
        if(page.equals("prev")){
            currentPage--;
        }
        else if(page.equals("next")){
            currentPage++;
        } else {
            currentPage = Integer.parseInt(page);
        }

        if(currentPage == 0){
            currentPage = 1;
        }

        //page in bootstrap template starts from 0
        pageNumber = currentPage-1;

        return pageNumber;
    }

}
