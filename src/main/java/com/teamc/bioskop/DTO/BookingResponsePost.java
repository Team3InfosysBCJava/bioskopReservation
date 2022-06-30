package com.teamc.bioskop.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponsePost {
    private Long reserv_id;
    private Integer sch_id;
    private Long usr_id;

    @Override
    public String toString() {
        return "BookingResponsePost{" +
                "reserv_id=" + reserv_id +
                ", sch_id=" + sch_id +
                ", usr_id=" + usr_id +
                '}';
    }
}
