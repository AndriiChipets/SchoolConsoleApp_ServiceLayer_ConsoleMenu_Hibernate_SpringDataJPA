package ua.prom.roboticsdmc.mapper;

import org.springframework.stereotype.Service;

import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.StudentDto;

@Service
public class StudentMapper implements Mapper<Student, StudentDto> {

    @Override
    public Student mapDomainToEntity(StudentDto studentDto) {
        return studentDto == null ? null :
            Student.builder()
                   .withUserId(studentDto.getUserId())
                   .withFirstName(studentDto.getFirstName())
                   .withLastName(studentDto.getLastName())
                   .withEmail(studentDto.getEmail())
                   .withPassword(studentDto.getPassword())
                   .withGroupId(studentDto.getGroupId())
                   .build();
    }

    @Override
    public StudentDto mapEntityToDomain(Student student) {
        return student == null ? null :
            StudentDto.builder()
                   .withUserId(student.getUserId())
                   .withFirstName(student.getFirstName())
                   .withLastName(student.getLastName())
                   .withEmail(student.getEmail())
                   .withPassword(student.getPassword())
                   .withGroupId(student.getGroupId())
                   .build();
    }
}
