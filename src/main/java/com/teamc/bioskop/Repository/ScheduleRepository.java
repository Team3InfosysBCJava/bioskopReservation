package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("Select s from Schedule s where s.films.name =?1")
    public List<Schedule> getScheduleByFilmName(String name);

    @Query("Select s from Schedule s where s.films.name like %:name%")
    public List<Schedule> getScheduleFilmsNameLike (@Param("name")String name);

    @Query("Select s from Schedule s where s.films.name like %:name% ORDER BY s.films.name ASC")
    public Page<Schedule> searchByName(@Param("name")String name, Pageable pageable);

}