package ua.prom.roboticsdmc.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.service.StudentService;
import ua.prom.roboticsdmc.view.ViewProvider;

@Service
public class FrontController {
    
   private static final String MENU = "\n\t ============ Please, choose what do you want to do ============\n"
            + "1 -> Find all groups with less or equal student quantity \n"
            + "2 -> Find all students related to the course with the given name \n"
            + "3 -> Add a student to the course \n"
            + "4 -> Remove the student from one of their courses \n" 
            + "5 -> CRUD_STUDENT save new student \n"
            + "6 -> CRUD_STUDENT delete student by the STUDENT_ID \n"
            + "0 -> To exit from the program \n";
   private static final String WRONG_CHOICE_MESSAGE = "Please, make right choice from the list or enter \"0\" to exit from the program";
   private final StudentService studentService;
   private final ViewProvider viewProvider;

   public FrontController(StudentService studentService, ViewProvider viewProvider) {
       this.studentService = studentService;
       this.viewProvider = viewProvider;
   }

   public void run() {
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
           case 5 -> addNewStudent();
           case 6 -> deleteStudentById();
           default -> viewProvider.printMessage(WRONG_CHOICE_MESSAGE);
           }
       }
   }
    
    private void findGroupWithStudentsQuantity() {
        viewProvider.printMessage("Enter students quantity: ");
        int studentNumber = viewProvider.readInt();
        List<Group> groups = studentService.findAllGroupsWithLessOrEqualsStudentCount(studentNumber);
        groups.forEach(e -> viewProvider.printMessage(e.toString()));
    }

    private void findStudentByCourseName() {
        viewProvider.printMessage("Choose course name from the list: ");
        findAllCourses();
        viewProvider.printMessage("Enter course name: ");
        String courseName = viewProvider.read();
        List<Student> courseStudents = studentService.findAllStudentsRelatedToCourseWithGivenName(courseName);        
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
        List<Course> courses = studentService.findAllStudentCoursesByStudentId(studentId);
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

    private void addNewStudent() {
        viewProvider.printMessage("Enter student first name: ");
        String firstName = viewProvider.read();
        viewProvider.printMessage("Enter student last name: ");
        String lastName = viewProvider.read();
        viewProvider.printMessage("Enter group ID: ");
        int groupId = viewProvider.readInt();
        Student student = Student.builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withGroupId(groupId)
                .build();
        studentService.addNewStudent(student);
    }

    private void deleteStudentById() {
        viewProvider.printMessage("Enter student ID: ");
        int studentId = viewProvider.readInt();
        studentService.deleteStudentByStudent_Id(studentId);
    }

    private void findAllCourses() {
        List<Course> allCourses = studentService.findAllStudentsCources();
        allCourses.forEach(e -> viewProvider.printMessage(e.toString()));
    }
}
