package com.teamc.bioskop.DTO;



import com.teamc.bioskop.Model.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {

    private Long user_id;

    private String user_name;

    private String email_id;

    private String pass_word;


    public User convertToEntity(){
        return User.builder().userId(this.user_id).username(this.user_name).emailId(this.email_id)
                .password(this.pass_word).build();


    }



}
