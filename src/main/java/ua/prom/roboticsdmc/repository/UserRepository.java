package ua.prom.roboticsdmc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, CustomizedUserRepository {

    Optional<User> findByEmail(String email);

}
