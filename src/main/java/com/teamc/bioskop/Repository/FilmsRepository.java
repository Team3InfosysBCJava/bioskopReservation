package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Films;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.teamc.bioskop.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface FilmsRepository extends JpaRepository<Films, Long> {

    @Query(value = "select * from films f where is_playing =?1", nativeQuery = true)
    public List<Films> getFilmByIsPlaying(Boolean isPlaying);

    @Query("SELECT f FROM Films f WHERE CONCAT(f.filmId, ' ', f.isPlaying, ' ', f.name, ' ') LIKE %?1%")
    public List<Films> search(String keyword);

    @Query("Select f from Films f where f.name like %:name%")
    public Page<Films> searchByName(@Param("name")String name, Pageable pageable);
}
