package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import lombok.extern.log4j.Log4j2;
import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.service.DataGenerator;

@Log4j2
@ua.prom.roboticsdmc.anotation.DataGenerator
public class DataGeneratorImpl implements DataGenerator {
    
    private static final List<String> FIRST_NAMES = new ArrayList<>(Arrays.asList(
            "James", "Robert", "John", "Michael", "David", "William", "Richard", "Joseph", "Thomas", "Christopher",
            "Mary", "Patricia", "Jennifer", "Linda", "Elizabeth", "Barbara", "Susan", "Jessica", "Sarah", "Karen"));
    private static final List<String> LAST_NAMES = new ArrayList<>(Arrays.asList(
            "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis", "Rodriguez", "Martinez",
            "Hernandez", "Lopez", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin"));
    private static final char[] EN_ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    private static final char[] NUMBERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
    private static final List<String> COURSE_NAMES = new ArrayList<>(Arrays.asList(
            "Math", "Biology", "Philosophy", "Literature", "Science of law", "Physics", "Chemistry", "Ukrainian",
            "English", "Astronomy"));

    @Override
    public List<Student> createRandomStudent(int studentsNumber) {
        log.info("Method start");
        log.info("Create random " + studentsNumber + " students");
        List<Student> studentNames = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < studentsNumber; i++) {
            Student student = Student.builder()
                    .withFirstName(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())))
                    .withLastName(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())))
                    .build();
            studentNames.add(student);
        }
        log.info("Random " + studentsNumber + " students are created");
        log.info("Method end");
        return studentNames;
    }

    @Override
    public List<Group> createRandomGroup(int groupNumber) {
        log.info("Method start");
        log.info("Create random " + groupNumber + " groups");
        List<Group> newGroups = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < groupNumber; i++) {
            StringBuilder lettersBldr = new StringBuilder();
            StringBuilder numbersBldr = new StringBuilder();
            for (int j = 0; j < 2; j++) {
                lettersBldr.append(EN_ALPHABET[random.nextInt(EN_ALPHABET.length)]);
                numbersBldr.append(NUMBERS[random.nextInt(NUMBERS.length)]);
            }
            newGroups.add(Group.builder().withGroupName(String.format("%s-%s", lettersBldr, numbersBldr)).build());
        }
        log.info("Random " + groupNumber + " groups are created");
        log.info("Method end");
        return newGroups;
    }

    @Override
    public List<Course> createCourse() {
        log.info("Method start");
        log.info("Create list of courses");
        List<Course> newCourses = new ArrayList<>();

        for (int i = 0; i < COURSE_NAMES.size(); i++) {
            Course course = Course.builder().withCourseName(COURSE_NAMES.get(i)).build();
            newCourses.add(course);
        }
        log.info("List of courses are created");
        log.info("Method end");
        return newCourses;
    }

    @Override
    public List<Student> assignStudentToGroup(List<Group> groups, List<Student> students, int minStudentInGroup,
            int maxStudentInGroup) {
        log.info("Method start");
        log.info("Assign students to groups");
        int studentsQuantity = students.size();
        boolean isFreeStudent = studentsQuantity > 0;

        for (int i = 1; i <= groups.size(); i++) {
            if (!isFreeStudent) {
                break;
            }
            int studentsInGroup = setRandomInterval(minStudentInGroup, maxStudentInGroup + 1);
            for (int j = 0; j < studentsInGroup; j++) {
                Student studentWithoutGroup = students.get(studentsQuantity - 1);
                Student studentWithGroup = Student.builder()
                        .withUserId(studentWithoutGroup.getUserId())
                        .withGroupId(i)
                        .withFirstName(studentWithoutGroup.getFirstName())
                        .withLastName(studentWithoutGroup.getLastName())
                        .build();
                students.set(studentsQuantity - 1, studentWithGroup);
                studentsQuantity--;
                if (studentsQuantity <= 0) {
                    isFreeStudent = false;
                    break;
                }
            }
        }
        log.info("Students are assigned to groups");
        log.info("Method end");
        return students;
    }

    @Override
    public List<Student> assignStudentToCourses(List<Student> students, List<Course> courses, int minStudentCourses,
            int maxStudentCourses) {
        log.info("Method start");
        log.info("Assign students to courses");
        Random random = new Random();
        List<Student> studentsAssignToCourses = new ArrayList<>();
        List<Course> coursesWithStudents = new ArrayList<>(courses);
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            Set<Course> studentCourses = new HashSet<>();
            int numCourcesPerStudent = random.nextInt(minStudentCourses, maxStudentCourses + 1);
            for (int j = 0; j < numCourcesPerStudent; j++) {
                Course course = coursesWithStudents.get(random.nextInt(coursesWithStudents.size()));
                studentCourses.add(course);
                int courseIndex = coursesWithStudents.indexOf(course);
                course.getStudents().add(student);
                coursesWithStudents.set(courseIndex, course);
            }
            Student studentAssignToCourse = Student.builder()
                    .withUserId(student.getUserId())
                    .withFirstName(student.getFirstName())
                    .withLastName(student.getLastName())
                    .withEmail(student.getEmail())
                    .withPassword(student.getPassword())
                    .withGroupId(student.getGroupId())
                    .withCourses(studentCourses)
                    .build();
            studentsAssignToCourses.add(studentAssignToCourse);
        }
        log.info("Students are assigned to courses");
        log.info("Method end");
        return studentsAssignToCourses;
    }

    private int setRandomInterval(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
