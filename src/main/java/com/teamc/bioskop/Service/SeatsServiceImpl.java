package com.teamc.bioskop.Service;
import com.teamc.bioskop.Exception.ResourceNotFoundException;
import com.teamc.bioskop.Model.Seats;
import com.teamc.bioskop.Repository.SeatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SeatsServiceImpl implements SeatsService {

    private final SeatsRepository seatRepository;
    public static Integer currentPage;

    //Get All
    @Override
    public List<Seats> findAllseats() {

        List<Seats> optionalSeats = seatRepository.findAll();
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("table seats have not value");
        }
        return seatRepository.findAll();
    }

    //Get by Id
    @Override
    public Optional<Seats> findbyid(Long id){
        Optional<Seats> optionalSeats = seatRepository.findById(id);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException(" Seats not Exist with id :" + id);
        }
        return seatRepository.findById(id);
    }

    //Post
    @Override
    public Seats createseat(Seats seat) {
        return seatRepository.save(seat);
    }
    //Update
    @Override
    public Seats getReferenceById (Long id) { return this.seatRepository.getReferenceById(id); }

    @Override
    public Seats updateseat(Seats seat, Long seatId) { return seatRepository.save(seat);
    }

//Delete
    @Override
    public void deleteseat(Long seatId){
        Optional<Seats> optionalSeats = seatRepository.findById(seatId);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id :" + seatId);
        }
        seatRepository.deleteAllById(Collections.singleton(seatId));

    }

//Custom Select
    @Override
    public List<Seats> getSeatAvailable(Boolean isAvailable) {
        List<Seats> optionalSeats = seatRepository.getSeatAvailable(isAvailable);
        if (optionalSeats.isEmpty()){
            throw new ResourceNotFoundException("Seats not exist with id : " + isAvailable);
        }
        return this.seatRepository.getSeatAvailable(isAvailable);
    }

    public List<Seats> search(String keyword) {
            if (keyword != null){
            return seatRepository.search(keyword);
        }
        return seatRepository.findAll();
    }

    @Override
    public Page<Seats> searchByNumber(String keyword, Integer page){
        if (keyword != null) {
            return seatRepository.searchByNumber(keyword, null);
        }else if (page == null){
            return seatRepository.findAll(PageRequest.of(0,10, Sort.by("seatId")));
        } else {
            return seatRepository.findAll(PageRequest.of(page,10, Sort.by("seatId")));
        }
    }

    @Override
    public Integer pageUpdate(String page) {

        //container
    Integer pageNumber = null;

    //check params
    if(page.equals("prev")){
        currentPage--;
    }
    else if(page.equals("next")){
        currentPage++;
    } else {
        currentPage = Integer.parseInt(page);
    }

    if(currentPage == 0){
        currentPage = 1;
    }

    //page in bootstrap template starts from 0
    pageNumber = currentPage-1;

    return pageNumber;
}
}
