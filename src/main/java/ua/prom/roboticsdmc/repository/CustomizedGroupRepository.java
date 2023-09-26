package ua.prom.roboticsdmc.repository;

import java.util.List;

import ua.prom.roboticsdmc.domain.Group;

public interface CustomizedGroupRepository {

    List<Group> findGroupWithLessOrEqualsStudentQuantity(Integer studentsQuantity);

}
