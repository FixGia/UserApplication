package fr.bewee.userapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private String city;

    private String password;

}
