package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Model.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Reservation,Long> {
    @Query("Select r from Reservation r where r.schedule.films.name like %:name%")
    public List<Reservation> getBookingByFilmName(@Param("name")String name);

    @Query("SELECT r FROM Reservation r WHERE CONCAT(r.reservationId, ' ', " +
            "r.schedule.films.name, ' ', r.schedule.films.isPlaying, ' ', " +
            "r.schedule.scheduleId, ' ', r.schedule.dateShow, ' ', r.schedule.showStart, ' ', r.schedule.showEnd, ' ', r.schedule.price, ' ', " +
            "r.schedule.seats.seatId, ' ', r.schedule.seats.seatNumber, ' ', r.schedule.seats.studioName, ' ', r.schedule.seats.isAvailable, ' ', " +
            "r.user.userId, ' ', r.user.username, ' ', r.user.emailId) LIKE %?1% ORDER BY r.reservationId ASC")
    public Page<Reservation> search(String keyword, Pageable pageable);

    @Query("Select r from Reservation r where r.schedule.films.name like %:name% ORDER BY r.schedule.films.name ASC")
    public Page<Reservation> getBookingFilm(@Param("name")String name , Pageable pageable);

    @Query("Select r from Reservation r where r.reservationId =?1 ORDER BY r.reservationId ASC")
    public Page<Reservation> getBookingId(Long id , Pageable pageable);


}

