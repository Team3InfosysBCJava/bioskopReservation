package com.teamc.bioskop.DTO;

import com.teamc.bioskop.Model.Seats;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class SeatsRequestDTO {
    private Long seatId;
    private Long seatNumber;
    private String studioName;
    private Boolean isAvailable;
    public Seats convertToEntity(){
        return Seats.builder().seatId(this.seatId).seatNumber(this.seatNumber).studioName(this.studioName).isAvailable(this.isAvailable).build();
    }
}
