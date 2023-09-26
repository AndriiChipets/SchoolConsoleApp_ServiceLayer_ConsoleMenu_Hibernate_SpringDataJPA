package ua.prom.roboticsdmc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.prom.roboticsdmc.domain.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, CustomizedStudentRepository {

}
