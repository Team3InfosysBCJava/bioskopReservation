package com.teamc.bioskop.Service;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Films;
import com.teamc.bioskop.Model.Seats;
import com.teamc.bioskop.Repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SeatsServiceImpl implements SeatsService {

    private final SeatsRepository seatRepository;

    @Autowired
    public SeatsServiceImpl(SeatsRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<Seats> findAllseats() {

        List<Seats> optionalSeats = seatRepository.findAll();
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("table seats have not value");
        }
        return seatRepository.findAll();
    }

    public Optional<Seats> findbyid(Long id){

        Optional<Seats> optionalSeats = seatRepository.findById(id);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException(" Seats not Exist with id :" + id);
        }
        return seatRepository.findById(id);
    }

    public Seats createseat(Seats seat) {
        return seatRepository.save(seat);}

    public Seats updateseat(Seats seat, Long seatId) {
        Optional<Seats> optionalSeats = seatRepository.findById(seatId);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Films not exist with id" + seatId);
        }
        return seatRepository.save(seat);
    }


    public void deleteseat(Long seatId){
        Optional<Seats> optionalSeats = seatRepository.findById(seatId);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id :" + seatId);
        }
        Seats seats = seatRepository.getReferenceById(seatId);
        this.seatRepository.delete(seats);

    }

    @Override
    public Seats getReferenceById(Long id) {
        return this.seatRepository.getReferenceById(id);
    }

    @Override
    public List<Seats> getSeatAvailable(Integer isAvailable) {
        List<Seats> optionalSeats = seatRepository.getSeatAvailable(isAvailable);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id : " + isAvailable);
        }
        return this.seatRepository.getSeatAvailable(isAvailable);
    }

}
