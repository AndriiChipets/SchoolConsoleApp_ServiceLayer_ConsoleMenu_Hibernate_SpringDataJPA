package ua.prom.roboticsdmc.mapper;

import org.springframework.stereotype.Service;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.dto.CourseDto;

@Service
public class CourseMapper implements Mapper<Course, CourseDto> {

    @Override
    public Course mapDomainToEntity(CourseDto courseDto) {
        return courseDto == null ? null :
            Course.builder()
                    .withCourseId(courseDto.getCourseId())
                    .withCourseName(courseDto.getCourseName())
                    .withCourseDescription(courseDto.getCourseDescription())
                    .build();
    }

    @Override
    public CourseDto mapEntityToDomain(Course course) {
        return course == null ? null :
            CourseDto.builder()
                    .withCourseId(course.getCourseId())
                    .withCourseName(course.getCourseName())
                    .withCourseDescription(course.getCourseDescription())
                    .build();
    }
}
