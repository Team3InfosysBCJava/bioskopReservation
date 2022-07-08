package com.teamc.bioskop.Service;

import com.teamc.bioskop.Model.Seats;

import java.util.List;
import java.util.Optional;

public interface SeatsService {

    List<Seats> findAllseats();
    Optional<Seats> findbyid(Long id);
    Seats createseat(Seats seat);
    Seats updateseat(Seats seat, Long seatId);
    void deleteseat(Long seatId);
    Seats getReferenceById (Long id);
    List<Seats> getSeatAvailable(Integer isAvailable);
    List<Seats> search(String keyword);
}
