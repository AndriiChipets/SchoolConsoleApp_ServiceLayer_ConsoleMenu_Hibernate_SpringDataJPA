package ua.prom.roboticsdmc.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ua.prom.roboticsdmc.domain.Course;
import ua.prom.roboticsdmc.domain.Group;
import ua.prom.roboticsdmc.domain.Student;
import ua.prom.roboticsdmc.service.DataGenerator;

@ua.prom.roboticsdmc.anotation.DataGenerator
public class DataGeneratorImpl implements DataGenerator {
    
    @Override
    public List<Student> createRandomStudent(int studentsNumber) {

        List<Student> studentNames = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < studentsNumber; i++) {
            Student student = Student.builder()
                    .withFirstName(ConstantUtility.FIRST_NAMES.get(random.nextInt(ConstantUtility.FIRST_NAMES.size())))
                    .withLastName(ConstantUtility.LAST_NAMES.get(random.nextInt(ConstantUtility.LAST_NAMES.size())))
                    .build();
            studentNames.add(student);
        }
        return studentNames;
    }

    @Override
    public List<Group> createRandomGroup(int groupNumber) {

        List<Group> newGroups = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < groupNumber; i++) {
            StringBuilder lettersBldr = new StringBuilder();
            StringBuilder numbersBldr = new StringBuilder();
            for (int j = 0; j < 2; j++) {
                lettersBldr.append(ConstantUtility.EN_ALPHABET[random.nextInt(ConstantUtility.EN_ALPHABET.length)]);
                numbersBldr.append(ConstantUtility.NUMBERS[random.nextInt(ConstantUtility.NUMBERS.length)]);
            }
            newGroups.add(Group.builder().withGroupName(String.format("%s-%s", lettersBldr, numbersBldr)).build());
        }
        return newGroups;
    }

    @Override
    public List<Course> createCourse() {

        List<Course> newCourses = new ArrayList<>();

        for (int i = 0; i < ConstantUtility.COURSE_NAMES.size(); i++) {
            Course course = Course.builder().withCourseName(ConstantUtility.COURSE_NAMES.get(i)).build();
            newCourses.add(course);
        }
        return newCourses;
    }

    @Override
    public List<Student> assignStudentToGroup(List<Group> groups, List<Student> students, int minStudentInGroup,
            int maxStudentInGroup) {

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
        return students;
    }

    @Override
    public List<List<Integer>> assignStudentToCourses(List<Student> students, List<Course> courses,
            int minStudentCourses, int maxStudentCourses) {

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
        return studentCourses;
    }

    public int setRandomInterval(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
