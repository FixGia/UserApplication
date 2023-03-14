package fr.bewee.userapplication.controller;

import fr.bewee.userapplication.dto.UserDto;
import fr.bewee.userapplication.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/update/{id}")
    public UserDto updateUser(@PathVariable("id") UUID UserId, @RequestBody final UserDto user) {

        log.debug("Controller UserApplication: updateUser - Called");
        userService.updateUserById(UserId, user);

        return user;


    }

    @PostMapping("/addUser")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody final UserDto user) {

        log.debug("Controller UserApplication: create User - Called");

        userService.createUser(user);

        return user;

    }

    @PostMapping("/deleteUser/{username}")
    public void deletePatient(@PathVariable("username")String username){

        log.debug("Controller UserApplication: deleteUser - called");
        userService.deleteUserByUsername(username);
    }

    @PostMapping("/replaceCity/{id}")
    public void replaceCity(@PathVariable("id") UUID userId, String city) {
        log.debug("Controller UserApplication: updateCity - called");
        userService.replaceCity(userId, city);
    }

}
