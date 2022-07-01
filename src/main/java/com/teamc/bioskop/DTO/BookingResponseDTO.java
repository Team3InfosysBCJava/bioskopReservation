package com.teamc.bioskop.DTO;

import com.teamc.bioskop.Model.Schedule;
import com.teamc.bioskop.Model.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long reservation_id;
    private Long user_id;
    private String user_name;
    private String email_user;
    private String film_name;
    private Integer price;
    private String studio;
    private Integer status_show;
    private Long seat_num;
    private Integer status_seat;
    private LocalDate date_film;
    private LocalTime start_film;
    private LocalTime end_film;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @Override
    public String toString() {
        return "BookingResponseDTO{" +
                "reservation_id=" + reservation_id +
                ", user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", email_user='" + email_user + '\'' +
                ", film_name='" + film_name + '\'' +
                ", price=" + price +
                ", studio='" + studio + '\'' +
                ", status_show=" + status_show +
                ", seat_num=" + seat_num +
                ", status_seat=" + status_seat +
                ", date_film=" + date_film +
                ", start_film=" + start_film +
                ", end_film=" + end_film +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                '}';
    }
}

