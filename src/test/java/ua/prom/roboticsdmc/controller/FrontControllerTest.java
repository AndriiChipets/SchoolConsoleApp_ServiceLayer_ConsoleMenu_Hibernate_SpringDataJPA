package ua.prom.roboticsdmc.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
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

import ua.prom.roboticsdmc.domain.UserRegistrationRequest;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.service.StudentService;
import ua.prom.roboticsdmc.service.UserService;
import ua.prom.roboticsdmc.view.ViewProvider;

@SpringBootTest(classes = {FrontController.class})
@DisplayName("FrontControllerTest")
class FrontControllerTest {

    @MockBean
    StudentService studentService;

    @MockBean
    UserService userService;

    @MockBean
    ViewProvider viewProvider;

    @Autowired
    FrontController frontController;

    @Test
    @DisplayName("run() should invoke findGroupWithStudentsQuantity()")
    void run_shouldInvokeMethodFindGroupWithStudentsQuantity_whenChoiceIs1() {

        String email = "test@gmail.com";
        String password = "12@Ab34";
        int studentNumber = 15;
        List<GroupDto> groups = new ArrayList<>(Arrays.asList(
                GroupDto.builder()
                .withGroupName("AA-01")
                .build(),
                GroupDto.builder()
                .withGroupName("BB-02")
                .build()));
        
        when(viewProvider.read()).thenReturn(email).thenReturn(password);
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(1).thenReturn(studentNumber).thenReturn(0);
        when(studentService.findAllGroupsWithLessOrEqualsStudentCount(anyInt())).thenReturn(groups);
        frontController.run();

        verify(studentService).findAllGroupsWithLessOrEqualsStudentCount(studentNumber);
    }

    @Test
    @DisplayName("run() should invoke findStudentByCourseName()")
    void run_shouldInvokeMethodFindStudentByCourseName_whenChoiceIs2() {

        String courseName = "Ukranian";      
        List<CourseDto> courses = new ArrayList<>(Arrays.asList(
                CourseDto.builder()
                .withCourseId(1)
                .withCourseName("Ukranian")
                .build(),
                CourseDto.builder()
                .withCourseId(2)
                .withCourseName("Physics")
                .build()));       
        List<StudentDto> courseStudents = new ArrayList<>(Arrays.asList(
                StudentDto.builder().withUserId(1)
                                 .withGroupId(5)
                                 .withFirstName("Christopher")
                                 .withLastName("Thomas")
                                 .build(),
                StudentDto.builder().withUserId(2)
                                 .withGroupId(3)
                                 .withFirstName("Patricia")
                                 .withLastName("Wilson")
                                 .build()));

        when(viewProvider.readInt()).thenReturn(2).thenReturn(0);
        when(studentService.findAllStudentsCources()).thenReturn(courses);
        when(viewProvider.read()).thenReturn(courseName);
        when(studentService.findAllStudentsRelatedToCourseWithGivenName(anyString())).thenReturn(courseStudents);
        frontController.run();

        verify(studentService).findAllStudentsRelatedToCourseWithGivenName(courseName);
    }

    @Test
    @DisplayName("run() should invoke addStudentToCourse()")
    void run_shouldInvokeMethodAddStudentToCourse_whenChoiceIs3() {

        String email = "test@gmail.com";
        String password = "12@Ab34";
        int studentId = 10;
        int courseId = 1;
        List<CourseDto> courses = new ArrayList<>(Arrays.asList(
                CourseDto.builder()
                .withCourseId(1)
                .withCourseName("Ukranian")
                .build(),
                CourseDto.builder()
                .withCourseId(2)
                .withCourseName("Physics")
                .build()));

        when(viewProvider.read()).thenReturn(email).thenReturn(password);
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(3).thenReturn(courseId).thenReturn(studentId).thenReturn(0);
        when(studentService.findAllStudentsCources()).thenReturn(courses);
        frontController.run();

        verify(studentService).addStudentToCourse(studentId, courseId);
    }

    @Test
    @DisplayName("run() should invoke removeStudentFromCourse()")
    void run_shouldInvokeMethodRemoveStudentFromCourse_whenChoiceIs4() {

        String email = "test@gmail.com";
        String password = "12@Ab34";
        int studentId = 10;
        int courseId = 1;
        List<CourseDto> courses = new ArrayList<>(Arrays.asList(
                CourseDto.builder()
                .withCourseId(1)
                .withCourseName("Ukranian")
                .build(),
                CourseDto.builder()
                .withCourseId(2)
                .withCourseName("Physics")
                .build()));

        when(viewProvider.read()).thenReturn(email).thenReturn(password);
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(4).thenReturn(studentId).thenReturn(courseId).thenReturn(0);
        when(studentService.findAllStudentCoursesByStudentId(anyInt())).thenReturn(courses);
        frontController.run();

        verify(studentService).removeStudentFromOneOfTheirCourses(studentId, courseId);
    }

    @Test
    @DisplayName("run() should invoke addNewUser()")
    void run_shouldInvokeMethodAddNewUser_whenChoiceIs5() {

        String email = "test@gmail.com";
        String password = "1aB@cd1";
        String repeatPassword = "1aB@cd1";
        String firstName = "James";
        String lastName = "Garcia";
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .withEmail(email)
                .withPassword(password)
                .withRepeatPassword(repeatPassword)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();
        
        when(viewProvider.read())
        .thenReturn(email)
        .thenReturn(password)
        .thenReturn(email)
        .thenReturn(password)
        .thenReturn(repeatPassword)
        .thenReturn(firstName)
        .thenReturn(lastName);;
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(5).thenReturn(0);

        frontController.run();

        verify(userService).register(userRegistrationRequest);
    }
    
    @Test
    @DisplayName("run() should throw IllegalArgumentException when password and repeat password are not equal")
    void run_shouldshouldThrowIllegalArgumentException_whenPasswordAndRepeatPasswordAreNotEqual() {
        
        String email = "test@gmail.com";
        String password = "1aB@cd1";
        String wrongRepeatPassword = "2aB@cd!";
        String firstName = "James";
        String lastName = "Garcia";
        when(viewProvider.readInt()).thenReturn(5).thenReturn(0);
        when(viewProvider.read())
        .thenReturn(email)
        .thenReturn(password)
        .thenReturn(wrongRepeatPassword)
        .thenReturn(firstName)
        .thenReturn(lastName);
        
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> frontController.run());
        assertEquals("password and repeat password are not equal", exception.getMessage());

    }

    
    @Test
    @DisplayName("run() should invoke deleteUserById()")
    void run_shouldInvokeMethodDeleteUserById_whenChoiceIs9() {

        String email = "test@gmail.com";
        String password = "12@Ab34";
        int userId = 10;
        
        when(viewProvider.read()).thenReturn(email).thenReturn(password);
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(6).thenReturn(userId).thenReturn(0);
        frontController.run();
        
        verify(studentService, times(1)).deleteUserByUser_Id(userId);
    }
    
    @Test
    @DisplayName("run() should invoke addStudentToGroup()")
    void run_shouldInvokeMethodAddStudentToGroup_whenChoiceIs9() {

        String email = "test@gmail.com";
        String password = "12@Ab34";
        int userId = 10;
        int groupId = 1;

        when(viewProvider.read()).thenReturn(email).thenReturn(password);
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(7).thenReturn(userId).thenReturn(groupId).thenReturn(0);
        frontController.run();

        verify(studentService).addStudentToGroup(groupId, userId);
    }

    @Test
    @DisplayName("run() should print message when choice is incorrect")
    void run_shouldPrintMessage_whenChoiceIsIncorrect() {

        String email = "test@gmail.com";
        String password = "12@Ab34";
        int choise = 1000;
        String wrongChoiceMessage = "Please, make right choice from the list or enter \"0\" to exit from the program";

        when(viewProvider.read()).thenReturn(email).thenReturn(password);
        when(userService.login(anyString(), anyString())).thenReturn(true);
        when(viewProvider.readInt()).thenReturn(choise).thenReturn(0);
        frontController.run();

        verify(viewProvider).printMessage(wrongChoiceMessage);
    }
}
