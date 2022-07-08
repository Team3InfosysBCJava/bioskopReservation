package com.teamc.bioskop.Repository;

import com.teamc.bioskop.Model.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

@Repository
public interface SeatsRepository extends JpaRepository<Seats, Long> {
    @Query(value = "select * from seats s where is_available =?1", nativeQuery = true)
    public List<Seats> getSeatAvailable(Integer isAvailable);

    @Query("SELECT s2 FROM Seats s2 WHERE CONCAT(s2.seatId, ' ', s2.seatNumber, ' ', s2.studioName, ' ', s2.isAvailable, ' ') LIKE %?1%")
    public List<Seats> search(String keyword);

}