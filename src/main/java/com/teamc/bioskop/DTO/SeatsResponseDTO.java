package com.teamc.bioskop.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatsResponseDTO {
    private Long seatId;
    private Long seatNumber;
    private String studioName;
    private Boolean isAvailable;

    @Override
    public String toString() {
        return "SeatsResponseDTO{" +
                "Seat Id=" + seatId +
                ", Seat Number=" + seatNumber +
                ", Studio Name='" + studioName + '\'' +
                ", Available='" + isAvailable + '\'' +

                '}';
    }
}
