package com.teamc.bioskop.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleResponseNameLikeDTO {
    private String filmName;
    private String studioName;
    private Integer price;
    private LocalDateTime created_at;
    private LocalDateTime updatedAt;

    @Override
    public String toString() {
        return "ScheduleResponseNameLikeDTO{" +
                "filmName='" + filmName + '\'' +
                ", studioName='" + studioName + '\'' +
                ", price=" + price +
                ", created_at=" + created_at +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
