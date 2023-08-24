package ua.prom.roboticsdmc.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import ua.prom.roboticsdmc.dao.CourseDao;
import ua.prom.roboticsdmc.dao.GroupDao;
import ua.prom.roboticsdmc.dao.StudentDao;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.mapper.CourseMapperStruct;
import ua.prom.roboticsdmc.mapper.GroupMapperStruct;
import ua.prom.roboticsdmc.mapper.StudentMapperStruct;

@SpringBootTest(classes = {StudentServiceImpl.class})
@DisplayName("StudentServiceImplTest")
class StudentServiceImplTest {

    @MockBean
    StudentDao studentDao;
    
    @MockBean
    CourseDao courseDao;
    
    @MockBean
    GroupDao groupDao;
    
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

        when(groupDao.findGroupWithLessOrEqualsStudentQuantity(anyInt())).thenReturn(groups);
        when(groupMapperStruct.mapGroupToGroupDto(any(Group.class)))
        .thenReturn(groupDto1)
        .thenReturn(groupDto2)
        .thenReturn(groupDto3);

        List<GroupDto> actualGroupDto = studentServiceImpl.findAllGroupsWithLessOrEqualsStudentCount(studentQuantity);

        assertNotNull(actualGroupDto);
        assertEquals(expectedGroupDto, actualGroupDto);

        verify(groupDao).findGroupWithLessOrEqualsStudentQuantity(anyInt());
    }
    
    @Test
    @DisplayName("findAllStudentsRelatedToCourseWithGivenName method should return list of Students")
    void findAllStudentsRelatedToCourseWithGivenName_shouldReturnListOfStudents_whenStudentsAssignToCourse() {

        String courseName = "Biology";
        
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
        List<Student> students = new ArrayList<Student>(Arrays.asList(student1, student2));
        
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

        when(studentDao.findStudentsByCourseName(anyString())).thenReturn(students);
        when(studentMapperStruct.mapStudentToStudentDto(any(Student.class)))
        .thenReturn(studentDto1)
        .thenReturn(studentDto2);

        List<StudentDto> actualStudentDto = studentServiceImpl.findAllStudentsRelatedToCourseWithGivenName(courseName);

        assertNotNull(actualStudentDto);
        assertEquals(expectedStudentDto, actualStudentDto);

        verify(studentDao).findStudentsByCourseName(anyString());
    }
    
    @Test
    @DisplayName("deleteUserByUser method should delete User when there is User with entered UserId")
    void deleteUserByUser_Id_shouldDeleteUser_whenThereIsUserWithEnteredUserId() {

        int userId = 20;
        studentServiceImpl.deleteUserByUser_Id(userId);

        verify(studentDao).deleteById(userId);
    }
    
    @Test
    @DisplayName("addStudentToCourse method should add Student to Course when entered data are correct")
    void addStudentToCourse_shouldAddStudentToCourse_whenEnteredDataAreCorrect() {

        int studentId = 10;
        int courseId = 5;
        
        studentServiceImpl.addStudentToCourse(studentId, courseId);

        verify(courseDao).addStudentToCourse(studentId, courseId);
    }
    
    @Test
    @DisplayName("removeStudentFromOneOfTheirCourses method should remove Student from one of his Course when entered data are correct")
    void removeStudentFromOneOfTheirCourses_shouldRemoveStudentFromHisCourse_whenEnteredDataAreCorrect() {

        int studentId = 10;
        int courseId = 5;
        
        studentServiceImpl.removeStudentFromOneOfTheirCourses(studentId, courseId);

        verify(courseDao).removeStudentFromCourse(studentId, courseId);
    }
    
    @Test
    @DisplayName("addStudentToGroup method should add Student toGroup when entered data are correct")
    void addStudentToGroup_shouldAddStudentToGroup_whenEnteredDataAreCorrect() {

        int studentId = 20;
        int groupId = 1;
        
        studentServiceImpl.addStudentToGroup(groupId, studentId);

        verify(studentDao).addStudentToGroup(groupId, studentId);
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

        when(courseDao.findAll()).thenReturn(courses);
        when(courseMapperStruct.mapCourseToCourseDto(any(Course.class))).thenReturn(courseDto1).thenReturn(courseDto2);

        List<CourseDto> actualCourseDto = studentServiceImpl.findAllStudentsCources();

        assertNotNull(actualCourseDto);
        assertEquals(expectedCourseDto, actualCourseDto);

        verify(courseDao).findAll();
    }
    
    @Test
    @DisplayName("findAllStudentCoursesByStudentId method should return list of Student Courses")
    void findAllStudentCoursesByStudentId_shouldReturnListOfStudentCourses_whenThereAreSomeStudentCourses() {

        int studentId = 5;
        Course course1 = Course.builder().withCourseId(1).withCourseName("Math").build();
        Course course2 = Course.builder().withCourseId(2).withCourseName("Biology").build();
        List<Course> courses = new ArrayList<>(Arrays.asList(course1, course2));

        CourseDto courseDto1 = CourseDto.builder().withCourseId(1).withCourseName("Math").build();
        CourseDto courseDto2 = CourseDto.builder().withCourseId(2).withCourseName("Biology").build();
        List<CourseDto> expectedCourseDto = new ArrayList<>(Arrays.asList(courseDto1, courseDto2));

        when(courseDao.getAllStudentCoursesByStudentID(anyInt())).thenReturn(courses);
        when(courseMapperStruct.mapCourseToCourseDto(any(Course.class))).thenReturn(courseDto1).thenReturn(courseDto2);

        List<CourseDto> actualCourseDto = studentServiceImpl.findAllStudentCoursesByStudentId(studentId);

        assertNotNull(actualCourseDto);
        assertEquals(expectedCourseDto, actualCourseDto);

        verify(courseDao).getAllStudentCoursesByStudentID(studentId);
    }
    
}
