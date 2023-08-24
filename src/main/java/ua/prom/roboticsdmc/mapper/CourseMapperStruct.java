package ua.prom.roboticsdmc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.dto.CourseDto;

@Mapper(componentModel = "spring")
public interface CourseMapperStruct {

    CourseMapperStruct INSTANCE = Mappers.getMapper(CourseMapperStruct.class);

    CourseDto mapCourseToCourseDto(Course course);

    Course mapCourseDtoToCourse(CourseDto courseDto);

}
