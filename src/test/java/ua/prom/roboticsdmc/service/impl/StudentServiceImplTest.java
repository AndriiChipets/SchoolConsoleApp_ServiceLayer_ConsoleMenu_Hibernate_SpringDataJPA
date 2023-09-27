package ua.prom.roboticsdmc.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.mapper.CourseMapperStruct;
import ua.prom.roboticsdmc.mapper.GroupMapperStruct;
import ua.prom.roboticsdmc.mapper.StudentMapperStruct;
import ua.prom.roboticsdmc.repository.CourseRepository;
import ua.prom.roboticsdmc.repository.GroupRepository;
import ua.prom.roboticsdmc.repository.StudentRepository;
import ua.prom.roboticsdmc.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(classes = {StudentServiceImpl.class})
@DisplayName("StudentServiceImplTest")
class StudentServiceImplTest {

    @MockBean
    StudentRepository studentRepository;
    
    @MockBean
    UserRepository userRepository;
    
    @MockBean
    CourseRepository courseRepository;
    
    @MockBean
    GroupRepository groupRepository;
    
    @MockBean
    StudentMapperStruct studentMapperStruct;
    
    @MockBean
    CourseMapperStruct courseMapperStruct;
    
    @MockBean
    GroupMapperStruct groupMapperStruct;

    @Autowired
    StudentServiceImpl studentServiceImpl;
    
    @Test
    @DisplayName("findAllGroupsWithLessOrEqualsStudentCount method should return list of Groups")
    void findAllGroupsWithLessOrEqualsStudentCount_shouldReturnListOfGroups_whenThereAreSomeGroupWithLessOrEqualsStudentCount() {

        int studentQuantity = 10;
        Group group1 = Group.builder().withGroupId(1).withGroupName("YY-58").build();
        Group group2 = Group.builder().withGroupId(2).withGroupName("VA-90").build();
        Group group3 = Group.builder().withGroupId(3).withGroupName("VA-52").build();
        List<Group> groups = new ArrayList<>(Arrays.asList(group1, group2, group3));

        GroupDto groupDto1 = GroupDto.builder().withGroupId(1).withGroupName("YY-58").build();
        GroupDto groupDto2 = GroupDto.builder().withGroupId(2).withGroupName("VA-90").build();
        GroupDto groupDto3 = GroupDto.builder().withGroupId(3).withGroupName("VA-52").build();
        List<GroupDto> expectedGroupDto = new ArrayList<>(Arrays.asList(groupDto1, groupDto2, groupDto3));

        when(groupRepository.findGroupWithLessOrEqualsStudentQuantity(anyInt())).thenReturn(groups);
        when(groupMapperStruct.mapGroupToGroupDto(any(Group.class)))
        .thenReturn(groupDto1)
        .thenReturn(groupDto2)
        .thenReturn(groupDto3);

        List<GroupDto> actualGroupDto = studentServiceImpl.findAllGroupsWithLessOrEqualsStudentCount(studentQuantity);

        assertNotNull(actualGroupDto);
        assertEquals(expectedGroupDto, actualGroupDto);

        verify(groupRepository).findGroupWithLessOrEqualsStudentQuantity(anyInt());
    }
    
    @Test
    @DisplayName("findAllStudentsRelatedToCourseWithGivenName method should return list of Students")
    void findAllStudentsRelatedToCourseWithGivenName_shouldReturnListOfStudents_whenStudentsAssignToCourse() {

        String courseName = "Biology";
        Course mockCourse = mock(Course.class);
        
        Student student1 = Student.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build();
        Student student2 = Student.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build();
        Set<Student> students = new HashSet<Student>(Arrays.asList(student1, student2));
        
        StudentDto studentDto1 = StudentDto.builder()
                .withUserId(3)
                .withGroupId(2)
                .withFirstName("Patricia")
                .withLastName("Garcia")
                .withEmail("patricia.garcia@gmail.com")
                .build();
        StudentDto studentDto2 = StudentDto.builder()
                .withUserId(4)
                .withGroupId(4)
                .withFirstName("Patricia")
                .withLastName("Jackson")
                .withEmail("patricia.jackson@gmail.com")
                .build();
        List<StudentDto> expectedStudentDto = new ArrayList<>(Arrays.asList(studentDto1, studentDto2));

        when(courseRepository.findCourseByCourseName(anyString())).thenReturn(Optional.of(mockCourse));
        when(Optional.of(mockCourse).get().getStudents()).thenReturn(students);
        when(studentMapperStruct.mapStudentToStudentDto(any(Student.class)))
        .thenReturn(studentDto1)
        .thenReturn(studentDto2);

        List<StudentDto> actualStudentDto = studentServiceImpl.findAllStudentsRelatedToCourseWithGivenName(courseName);

        assertNotNull(actualStudentDto);
        assertEquals(expectedStudentDto, actualStudentDto);

        verify(courseRepository).findCourseByCourseName(courseName);
    }
    
    @Test
    @DisplayName("deleteUserByUser method should delete User when there is User with entered UserId")
    void deleteUserByUser_Id_shouldDeleteUser_whenThereIsUserWithEnteredUserId() {

        int userId = 20;
        studentServiceImpl.deleteUserById(userId);

        verify(userRepository).deleteById(userId);
    }
    
    @Test
    @DisplayName("addStudentToCourse method should add Student to Course when entered data are correct")
    void addStudentToCourse_shouldAddStudentToCourse_whenEnteredDataAreCorrect() {

        int userId = 10;
        int courseId = 5;
        
        Student student = Student.builder().withUserId(userId).build();
        Optional <Student> studentOptional = Optional.of(student);
        Course course = Course.builder().withCourseId(courseId).build();
        Optional <Course> courseOptional = Optional.of(course);
        
        when(studentRepository.findById(anyInt())).thenReturn(studentOptional);
        when(courseRepository.findById(anyInt())).thenReturn(courseOptional);
        
        studentServiceImpl.addStudentToCourse(userId, courseId);

        verify(studentRepository).addStudentToCourse(student, course);
    }
    
    @Test
    @DisplayName("removeStudentFromOneOfTheirCourses method should remove Student from one of his Course when entered data are correct")
    void removeStudentFromOneOfTheirCourses_shouldRemoveStudentFromHisCourse_whenEnteredDataAreCorrect() {

        int userId = 10;
        int courseId = 5;
        
        Student student = Student.builder().withUserId(userId).build();
        Optional <Student> studentOptional = Optional.of(student);
        Course course = Course.builder().withCourseId(courseId).build();
        Optional <Course> courseOptional = Optional.of(course);
        
        when(studentRepository.findById(anyInt())).thenReturn(studentOptional);
        when(courseRepository.findById(anyInt())).thenReturn(courseOptional);
        
        studentServiceImpl.removeStudentFromOneOfTheirCourses(userId, courseId);

        verify(studentRepository).removeStudentFromCourse(student, course);
    }
    
    @Test
    @DisplayName("addStudentToGroup method should add Student toGroup when entered data are correct")
    void addStudentToGroup_shouldAddStudentToGroup_whenEnteredDataAreCorrect() {

        int userId  = 20;
        int groupId = 1;
        Student student = Student.builder().withUserId(userId).withGroupId(groupId).build();
        
        studentServiceImpl.addStudentToGroup(userId, groupId);

        verify(studentRepository).addStudentToGroup(student);
    }
    
    @Test
    @DisplayName("findAllStudentsCources method should return list of Courses")
    void findAllStudentsCources_shouldReturnListOfCourses_whenThereAreSomeCourses() {

        Course course1 = Course.builder().withCourseId(1).withCourseName("Math").build();
        Course course2 = Course.builder().withCourseId(2).withCourseName("Biology").build();
        List<Course> courses = new ArrayList<>(Arrays.asList(course1, course2));

        CourseDto courseDto1 = CourseDto.builder().withCourseId(1).withCourseName("Math").build();
        CourseDto courseDto2 = CourseDto.builder().withCourseId(2).withCourseName("Biology").build();
        List<CourseDto> expectedCourseDto = new ArrayList<>(Arrays.asList(courseDto1, courseDto2));

        when(courseRepository.findAll()).thenReturn(courses);
        when(courseMapperStruct.mapCourseToCourseDto(any(Course.class))).thenReturn(courseDto1).thenReturn(courseDto2);

        List<CourseDto> actualCourseDto = studentServiceImpl.findAllCources();

        assertNotNull(actualCourseDto);
        assertEquals(expectedCourseDto, actualCourseDto);

        verify(courseRepository).findAll();
    }
    
    @Test
    @DisplayName("findAllStudentCoursesByStudentId method should return list of Student Courses")
    void findAllStudentCoursesByStudentId_shouldReturnListOfStudentCourses_whenThereAreSomeStudentCourses() {

        int userId = 5;
        Student mockStudent = mock(Student.class);
        Course course1 = Course.builder().withCourseId(1).withCourseName("Math").build();
        Course course2 = Course.builder().withCourseId(2).withCourseName("Biology").build();
        Set<Course> courses = new HashSet<>(Arrays.asList(course1, course2));

        CourseDto courseDto1 = CourseDto.builder().withCourseId(1).withCourseName("Math").build();
        CourseDto courseDto2 = CourseDto.builder().withCourseId(2).withCourseName("Biology").build();
        List<CourseDto> expectedCourseDto = new ArrayList<>(Arrays.asList(courseDto1, courseDto2));

        when(studentRepository.findById(anyInt())).thenReturn(Optional.of(mockStudent));
        when(Optional.of(mockStudent).get().getCourses()).thenReturn(courses);
        when(courseMapperStruct.mapCourseToCourseDto(any(Course.class))).thenReturn(courseDto1).thenReturn(courseDto2);

        List<CourseDto> actualCourseDto = studentServiceImpl.findAllStudentCoursesByStudentId(userId);

        assertNotNull(actualCourseDto);
        assertEquals(expectedCourseDto, actualCourseDto);

        verify(studentRepository).findById(userId);
    }
}
