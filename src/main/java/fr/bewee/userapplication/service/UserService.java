package fr.bewee.userapplication.service;

import fr.bewee.userapplication.dto.UserDto;
import fr.bewee.userapplication.entity.UserEntity;
import fr.bewee.userapplication.repository.UserEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@Slf4j
public class UserService implements IUserService, UserDetailsService {

    private final UserEntityRepository userEntityRepository;

    public UserService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }


    public void createUser(UserDto user) {

        UserEntity getUser = userEntityRepository.findUserEntityByEmail(user.getEmail());

        if (getUser == null) {

            UserEntity newUser = new UserEntity();
            newUser.setEmail(user.getEmail());
            newUser.setFirstname(user.getFirstname());
            newUser.setLastname(user.getLastname());
            newUser.setPassword(user.getPassword());
            newUser.setCity(user.getCity());
            userEntityRepository.save(newUser);
            log.info("Service : create new User {} - OK !", newUser.getEmail());
        }
        else log.error("Service : create new User {} - FAIL ! This user already exist", getUser.getEmail());
    }

    public void deleteUserByUsername(String username){
        UserEntity getUser = userEntityRepository.findUserEntityByEmail(username);

        if(Objects.equals(getUser.getEmail(), username)) {
            userEntityRepository.deleteById(getUser.getId());
            log.info("Service : delete User{} with username {} - OK!", getUser.getEmail(), username);
        }
        else log.error ("Service : delete User with UUID {} - FAIL! This user doesn't present in DB", username);
    }
    public void replaceCity(UUID userId, String city) {
        Optional<UserEntity> userToUpdate = userEntityRepository.findById(userId);

        if(userToUpdate.isPresent()) {
            userToUpdate.get().setCity(city);
            userEntityRepository.save(userToUpdate.get());
            log.info("Service : replace city's user{} - OK", city);
        }
    }
    public void updateUserById(UUID userId, UserDto user) {

            Optional<UserEntity> userToUpdate = userEntityRepository.findById(userId);

            if(userToUpdate.isPresent()) {

                userToUpdate.get().setFirstname(user.getFirstname());
                userToUpdate.get().setLastname(user.getLastname());
                userToUpdate.get().setCity(user.getCity());
                userEntityRepository.save(userToUpdate.get());
                log.info("Service : update User{} - OK", user.getEmail());
            }
            else log.error("Service : update User{} - FAIL", user.getEmail());
        }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userEntityRepository.findUserEntityByEmail(email);

        if(user == null) {
            log.error("Service : loadUserByUsername User {} - FAIL", email);
            throw new UsernameNotFoundException("User not exist in DB");
        } else {
            log.info("Service : loadUserByUsername User {} - SUCCESS", email);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole()));
            return new User(user.getEmail(), user.getPassword(), authorities);
        }
    }
}
