package com.teamc.bioskop.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponsePost {
    private Long reservation_id;
    private Integer schedule_id;
    private Long user_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @Override
    public String toString() {
        return "BookingResponsePost{" +
                "reservation_id=" + reservation_id +
                ", schedule_id=" + schedule_id +
                ", user_id=" + user_id +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}
