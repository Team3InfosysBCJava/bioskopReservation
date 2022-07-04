package com.teamc.bioskop.Model;

import com.teamc.bioskop.DTO.BookingResponseDTO;
import com.teamc.bioskop.DTO.BookingResponsePost;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public BookingResponseDTO convertToResponse(){
        return BookingResponseDTO.builder()
                .reservation_id(this.reservationId)
                .user_id(this.getUser().getUserId())
                .user_name(this.getUser().getUsername())
                .email_user(this.getUser().getEmailId())
                .film_name(this.getSchedule().getFilms().getName())
                .price(this.getSchedule().getPrice())
                .studio(this.getSchedule().getSeats().getStudioName())
                .status_show(this.getSchedule().getFilms().getIsPlaying())
                .seat_num(this.getSchedule().getSeats().getSeatNumber())
                .status_seat(this.getSchedule().getSeats().getIsAvailable())
                .date_film(this.getSchedule().getDateShow())
                .start_film(this.getSchedule().getShowStart())
                .end_film(this.getSchedule().getShowEnd())
                .created_at(this.createdAt)
                .updated_at(this.updatedAt)
                .build();
    }

    public BookingResponsePost convertToResponsePost(){
        return BookingResponsePost.builder()
                .reservation_id(this.reservationId)
                .user_id(this.getUser().getUserId())
                .schedule_id(this.getSchedule().getScheduleId())
                .created_at(this.createdAt)
                .updated_at(this.updatedAt)
                .build();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", user=" + user +
                ", schedule=" + schedule +
                '}';
    }
}

