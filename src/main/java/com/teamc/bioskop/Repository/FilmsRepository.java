package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmsRepository extends JpaRepository<Films, Long> {

    @Query(value = "select * from films f where is_playing =?1", nativeQuery = true)
    public List<Films> getFilmByIsPlaying(Integer isPlaying);

    @Query("SELECT f FROM Films f WHERE CONCAT(f.filmId, ' ', f.isPlaying, ' ', f.name, ' ') LIKE %?1%")
    public List<Films> search(String keyword);

}
