package ua.prom.roboticsdmc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.StudentDto;

@Mapper(componentModel = "spring")
public interface StudentMapperStruct {

    StudentMapperStruct INSTANCE = Mappers.getMapper(StudentMapperStruct.class);

    StudentDto mapStudentToStudentDto(Student student);

    Student mapStudentDtoToStudent(StudentDto studentDto);

}
