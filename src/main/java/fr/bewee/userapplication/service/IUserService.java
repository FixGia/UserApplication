package fr.bewee.userapplication.service;

import fr.bewee.userapplication.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    void updateUserById(UUID userId, UserDto user);

    void deleteUserByUsername(String username);

    void createUser(UserDto user);

    void replaceCity(UUID userId, String city);


}
