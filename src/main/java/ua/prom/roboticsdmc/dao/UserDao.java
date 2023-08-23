package ua.prom.roboticsdmc.dao;

import java.util.Optional;

import ua.prom.roboticsdmc.domain.User;

public interface UserDao extends CrudDao<Integer, User> {

    Optional<User> findByEmail(String email);

    boolean isAnyTableInDbSchema();

}
