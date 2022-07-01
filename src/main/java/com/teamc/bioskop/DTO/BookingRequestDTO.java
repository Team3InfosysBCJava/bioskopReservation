package com.teamc.bioskop.DTO;

import com.teamc.bioskop.Model.Reservation;
import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequestDTO {
    private Long id;
    private User user;
    private Schedule schedule;

    public Reservation covertToEntitiy(){
        return Reservation.builder()
                .reservationId(this.id)
                .user(this.user)
                .schedule(this.schedule)
                .build();
    }
}

