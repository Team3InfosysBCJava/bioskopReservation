package com.teamc.bioskop.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmsResponseDTO {
    private Long filmId;
    private String title;
    private Boolean status;

    @Override
    public String toString() {
        return "FilmsResponseDTO{" +
                "filmId=" + filmId +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}

