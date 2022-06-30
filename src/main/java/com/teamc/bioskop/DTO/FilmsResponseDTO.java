package com.teamc.bioskop.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmsResponseDTO {
    private Long code;
    private String title;
    private Integer status;

    @Override
    public String toString() {
        return "FilmsResponseDTO{" +
                "code=" + code +
                ", title='" + title + '\'' +
                ", status=" + status +
                '}';
    }
}

