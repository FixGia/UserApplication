package fr.bewee.userapplication.utils;

import fr.bewee.userapplication.dto.UserDto;
import fr.bewee.userapplication.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public UserEntity mapToUserEntity(UserDto userDto) {
        return new UserEntity(
            userDto.getId(),
            userDto.getEmail(),
            userDto.getFirstname(),
            userDto.getLastname(),
            userDto.getCity(),
            userDto.getPassword()
        );
    }

    public UserDto mapToUserDto(UserEntity userEntity) {
        return new UserDto(
            userEntity.getId(),
            userEntity.getEmail(),
            userEntity.getFirstname(),
            userEntity.getLastname(),
            userEntity.getCity(),
            userEntity.getPassword()
        );
    }
}
