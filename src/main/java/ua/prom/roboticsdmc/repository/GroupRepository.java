package ua.prom.roboticsdmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.domain.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer>, CustomizedGroupRepository {

}
