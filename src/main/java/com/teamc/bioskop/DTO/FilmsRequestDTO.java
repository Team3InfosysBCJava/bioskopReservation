package com.teamc.bioskop.DTO;

import com.teamc.bioskop.Model.Films;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmsRequestDTO {
    private String title;
    private Boolean status;

    public Films convertToEntity(){
        return Films.builder()
                .name(this.title)
                .isPlaying(this.status)
                .build();
    }
}
