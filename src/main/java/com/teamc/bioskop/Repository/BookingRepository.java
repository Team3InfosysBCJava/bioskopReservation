package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Reservation,Long> {
    @Query("Select r from Reservation r where r.schedule.films.name like %:name%")
    public List<Reservation> getBookingByFilmName(@Param("name")String name);

}

