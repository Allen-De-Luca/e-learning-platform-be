package it.rad.elearning_platform.repository;

import it.rad.elearning_platform.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo{

    //Optional<User> findByEmail(String email);


}
