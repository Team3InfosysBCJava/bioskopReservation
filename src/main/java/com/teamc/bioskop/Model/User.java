package com.teamc.bioskop.Model;

import com.teamc.bioskop.DTO.UserResponseDTO;
import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;


    @Column(name = "username")
    private String username;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "password")
    private String password;

    public UserResponseDTO convertToResponse(){
        return UserResponseDTO.builder()
                .user_id(this.userId)
                .email_id(this.emailId)
                .pass_word(this.password)
                .build();
    }









    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}