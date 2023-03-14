package fr.bewee.userapplication.repository;

import fr.bewee.userapplication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findUserEntityByEmail(String email);
}
