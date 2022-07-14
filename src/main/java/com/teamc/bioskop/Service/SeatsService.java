package com.teamc.bioskop.Service;

import com.teamc.bioskop.Model.Seats;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface SeatsService {

    List<Seats> findAllseats();
    Optional<Seats> findbyid(Long id);
    Seats createseat(Seats seat);
    Seats updateseat(Seats seat);
    void deleteseat(Long seatId);
    Seats getReferenceById (Long id);
    List<Seats> getSeatAvailable(Boolean isAvailable);
    Page<Seats> searchByNumber(String keyword, Integer page);
    Integer pageUpdate(String page);
    Page<Seats> Search(String keyword, Integer page);
}
