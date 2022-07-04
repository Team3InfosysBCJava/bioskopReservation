package com.teamc.bioskop.DTO;


import com.teamc.bioskop.Model.Reservation;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    private Long user_id;

    private Reservation reservation ;

    private String user_name;

    private String email_id;

    private String pass_word;

    @Override
    public String toString() {
        return "\n ScheduleResponseDTO{" +
                "userID=" + user_id +
                ", email=" + email_id +
                ", password=" + pass_word +
                ", username=" + user_name +
                '}';
    }

}
