package ua.prom.roboticsdmc.dao;

import java.util.List;

import ua.prom.roboticsdmc.domain.Student;

public interface StudentDao extends CrudDao<Integer, Student> {

    void distributeStudentsToGroups(List<Student> students);

    void addStudentToGroup(Integer studentId, Integer groupId);

    void fillRandomStudentCourseTable(List<Student> studentsAssignedToCourses);
}
