package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
        
        log.trace("Create random " + studentsNumber + " students");
        List<Student> studentNames = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < studentsNumber; i++) {
            Student student = Student.builder()
                    .withFirstName(FIRST_NAMES.get(random.nextInt(FIRST_NAMES.size())))
                    .withLastName(LAST_NAMES.get(random.nextInt(LAST_NAMES.size())))
                    .build();
            studentNames.add(student);
        }
        log.trace("Random " + studentsNumber + " students are created");
        return studentNames;
    }

    @Override
    public List<Group> createRandomGroup(int groupNumber) {

        log.trace("Create random " + groupNumber + " groups");
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
        log.trace("Random " + groupNumber + " groups are created");
        return newGroups;
    }

    @Override
    public List<Course> createCourse() {

        log.trace("Create list of courses");
        List<Course> newCourses = new ArrayList<>();

        for (int i = 0; i < COURSE_NAMES.size(); i++) {
            Course course = Course.builder().withCourseName(COURSE_NAMES.get(i)).build();
            newCourses.add(course);
        }
        log.trace("List of courses are created");
        return newCourses;
    }

    @Override
    public List<Student> assignStudentToGroup(List<Group> groups, List<Student> students, int minStudentInGroup,
            int maxStudentInGroup) {
        log.trace("Assign students to groups");
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
        log.trace("Students are assigned to groups");
        return students;
    }

    @Override
    public List<List<Integer>> assignStudentToCourses(List<Student> students, List<Course> courses,
            int minStudentCourses, int maxStudentCourses) {

        log.trace("Assign students to courses");
        List<List<Integer>> studentCourses = new ArrayList<>();
        List<Integer> coursesId = null;
        Random random = new Random();

        for (int i = 1; i <= students.size(); i++) {
            int numCourcesPerStudent = random.nextInt(minStudentCourses, maxStudentCourses + 1);
            coursesId = new ArrayList<>();
            for (int j = 0; j < numCourcesPerStudent; j++) {
                int randomCourseId = courses.get(random.nextInt(courses.size() - 1)).getCourseId();
                coursesId.add(randomCourseId);
            }
            studentCourses.add(coursesId);
        }
        log.trace("Students are assigned to courses");
        return studentCourses;
    }

    public int setRandomInterval(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
