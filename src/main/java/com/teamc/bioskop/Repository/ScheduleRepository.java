package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
    @Query("Select s from Schedule s where s.films.name =?1")
    public List<Schedule> getScheduleByFilmName(String name);

    @Query("Select s from Schedule s where s.films.name like %:name%")
    public List<Schedule> getScheduleFilmsNameLike (@Param("name")String name);

    @Query("Select s from Schedule s where s.films.name like %:name%")
    public List<Schedule> searchByName(@Param("name")String name);

//    @Query("SELECT s FROM Schedule s WHERE CONCAT(s.scheduleId, ' ', " +
//            "s.films.name, ' ', s.films.isPlaying, ' ', " +
//            "s.dateShow, ' ', s.showStart, ' ', s.showEnd, ' ', s.price, ' ', " +
//            "s.seats.seatId, ' ', s.seats.seatNumber, ' ', s.seats.studioName, ' ', s.seats.isAvailable, ' ') LIKE %?1%")
//    public List<Schedule> search(String keyword);
}