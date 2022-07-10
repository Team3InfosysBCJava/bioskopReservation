package com.teamc.bioskop.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.teamc.bioskop.DTO.SeatsResponseDTO;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Table(name = "Seats")
public class Seats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @Column(name = "seat_number")
    private long seatNumber;

    @Column(name = "studio_name")
    private String studioName;

    @Column(name = "is_available")
    private Boolean isAvailable;

    public Seats(long seatId, long seatNumber, String studioName, Boolean isAvailable){
        super();
        this.seatId = seatId;
        this.seatNumber = seatNumber;
        this.studioName = studioName;
        this.isAvailable = isAvailable;
    }

    public Long getSeatId() {

        return seatId;
    }

    public void setSeatId(Long seatId) {

        this.seatId = seatId;
    }

    public Long getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Long seatNumber) {

        this.seatNumber = seatNumber;
    }

    public String getStudioName() {

        return studioName;
    }

    public void setStudioName(String studioName) {

        this.studioName = studioName;
    }

    public Boolean getIsAvailable() {

        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {

        this.isAvailable = isAvailable;
    }
    public SeatsResponseDTO convertToResponses(){
        return SeatsResponseDTO.builder()
                .seatId(this.seatId)
                .seatNumber(this.seatNumber)
                .studioName(this.studioName)
                .isAvailable(this.isAvailable)
                .build();
    }
    @Override
    public String toString() {
        return "Seats{" +
                "seatId=" + seatId +
                ", seatNumber=" + seatNumber +
                ", studioName='" + studioName + '\'' +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
