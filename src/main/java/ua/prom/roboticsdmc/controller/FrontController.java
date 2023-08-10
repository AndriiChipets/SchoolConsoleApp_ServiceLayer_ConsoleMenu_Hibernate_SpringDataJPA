package ua.prom.roboticsdmc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.prom.roboticsdmc.domain.UserRegistrationRequest;
import ua.prom.roboticsdmc.dto.CourseDto;
import ua.prom.roboticsdmc.dto.GroupDto;
import ua.prom.roboticsdmc.dto.StudentDto;
import ua.prom.roboticsdmc.service.StudentService;
import ua.prom.roboticsdmc.service.UserService;
import ua.prom.roboticsdmc.view.ViewProvider;

@Service
@AllArgsConstructor
public class FrontController {
    
   private static final String MENU = "\n\t ============ Please, choose what do you want to do ============\n"
            + "1 -> Find all groups with less or equal student quantity \n"
            + "2 -> Find all students related to the course with the given name \n"
            + "3 -> Add student to the course \n"
            + "4 -> Remove student from one of his courses \n" 
            + "5 -> Register new user \n"
            + "6 -> Delete user by user_Id \n"
            + "7 -> Add student to group \n"
            + "0 -> To exit from the program \n";
   private static final String WRONG_CHOICE_MESSAGE = "Please, make right choice from the list or enter \"0\" to exit from the program";
   private final StudentService studentService;
   private final UserService userService;
   private final ViewProvider viewProvider;

   public void run() {
       if (!login()) {
           viewProvider.printMessage("You are not registred, please register");
           addNewUser();
           viewProvider.printMessage("Thank you, you are registred");
       }
       boolean isWork = true;
       while (isWork) {
           viewProvider.printMessage(MENU);
           int choice = viewProvider.readInt();
           switch (choice) {
           case 0 -> isWork = false;
           case 1 -> findGroupWithStudentsQuantity();
           case 2 -> findStudentByCourseName();
           case 3 -> addStudentToCourse();
           case 4 -> removeStudentFromCourse();
           case 5 -> addNewUser();
           case 6 -> deleteUserById();
           case 7 -> addStudentToGroup();
           default -> viewProvider.printMessage(WRONG_CHOICE_MESSAGE);
           }
       }
   }

   private boolean login() {
       viewProvider.printMessage("To work with program, please login");
       viewProvider.printMessage("Enter email:");
       String email = viewProvider.read();
       viewProvider.printMessage("Enter password:");
       String password = viewProvider.read();
       return userService.login(email, password);
   }

    private void findGroupWithStudentsQuantity() {
        viewProvider.printMessage("Enter students quantity: ");
        int studentNumber = viewProvider.readInt();
        List<GroupDto> groups = studentService.findAllGroupsWithLessOrEqualsStudentCount(studentNumber);
        groups.forEach(e -> viewProvider.printMessage(e.toString()));
    }

    private void findStudentByCourseName() {
        viewProvider.printMessage("Choose course name from the list: ");
        findAllCourses();
        viewProvider.printMessage("Enter course name: ");
        String courseName = viewProvider.read();
        List<StudentDto> courseStudents = studentService.findAllStudentsRelatedToCourseWithGivenName(courseName);        
        courseStudents.forEach(e -> viewProvider.printMessage(e.toString()));
    }

    private void addStudentToCourse() {
        viewProvider.printMessage("Choose course name from the list to assign student to this course by student ID: ");
        findAllCourses();
        viewProvider.printMessage("Enter course ID: ");
        int courseId = viewProvider.readInt();
        viewProvider.printMessage("Enter student ID: ");
        int studentId = viewProvider.readInt();
        studentService.addStudentToCourse(studentId, courseId);
    }

    private void getAllStudentCoursesByStudentID(Integer studentId) {
        List<CourseDto> courses = studentService.findAllStudentCoursesByStudentId(studentId);
        courses.forEach(e -> viewProvider.printMessage(e.toString()));
    }

    private void removeStudentFromCourse() {
        viewProvider.printMessage("Enter student ID who you want to remove from the course: ");
        int studentId = viewProvider.readInt();
        viewProvider.printMessage("Choose course from the list to remove student from his course by student ID: ");
        getAllStudentCoursesByStudentID(studentId);
        viewProvider.printMessage("Enter course ID: ");
        int courseId = viewProvider.readInt();
        studentService.removeStudentFromOneOfTheirCourses(studentId, courseId);
    }

    private void addNewUser() {
        viewProvider.printMessage("Enter user email: ");
        String email = viewProvider.read();
        viewProvider.printMessage("Enter user password: ");
        String password = viewProvider.read();
        viewProvider.printMessage("Repeat user password: ");
        String repeatPassword = viewProvider.read();
        if (!password.equals(repeatPassword)) {
            throw new IllegalArgumentException("password and repeat password are not equal");
        }
        viewProvider.printMessage("Enter user first name: ");
        String firstName = viewProvider.read();
        viewProvider.printMessage("Enter user last name: ");
        String lastName = viewProvider.read();
        UserRegistrationRequest userRegistrationRequest = UserRegistrationRequest.builder()
                .withEmail(email)
                .withPassword(password)
                .withRepeatPassword(repeatPassword)
                .withFirstName(firstName)
                .withLastName(lastName)
                .build();
        userService.register(userRegistrationRequest);
    }

    private void deleteUserById() {
        viewProvider.printMessage("Enter student ID: ");
        int userId = viewProvider.readInt();
        studentService.deleteUserByUser_Id(userId);
    }

    private void findAllCourses() {
        List<CourseDto> allCourses = studentService.findAllStudentsCources();
        allCourses.forEach(e -> viewProvider.printMessage(e.toString()));
    }
    
    private void addStudentToGroup() {
        viewProvider.printMessage("Enter student ID: ");
        int studentId = viewProvider.readInt();
        viewProvider.printMessage("Enter group ID: ");
        int groupId = viewProvider.readInt();
        studentService.addStudentToGroup(groupId, studentId);
    }
}
