This application was implemented step by step and the final code responds to the last description - Spring Data JPA.
If you are interesting in ditails, you are able to see intermediate implementation in commits.

# For Service Layer
1. Created a service layer on the top of the DAO to implement the following requirements:
-Find all groups with less or equals student count
-Find all students related to the course with the given name
-Add a new student
-Delete a student by STUDENT_ID
-Add a student to the course
-Remove the student from one of their courses
2. Added missing DAO methods to accomplish services needs
3. Created a generator service that will be called if database is empty:
-Create 10 groups with randomly generated names. The name should contain 2 characters, hyphen, 2 numbers
-Create 10 courses (math, biology, etc.)
-Create 200 students. Take 20 first names and 20 last names and randomly combine them to generate students.
-Randomly assign students to the groups. Each group can contain from 10 to 30 students. It is possible that some groups are without students or ---students without groups
4. Created the relation MANY-TO-MANY between the tables STUDENTS and COURSES. Randomly assign from 1 to 3 courses for each student

# For Console menu
1. Added a proper logging to the existing code
2. Created a console menu to utilize implemented functionality:
- Find all the groups with less or equal student count
- Find all the students related to the course with the given name
- Add a new student
- Delete student by STUDENT_ID
- Add a student to the course (from the list)
- Remove the student from one of their courses

# For Hibernate
1. Added the Hibernate support to the project
2. Enriched the model with JPA annotations Rewrited the DAO layer. Used Hibernate instead of Spring JDBC.
3. Rewrote DAO to use EntityManager instead of JDBCTemplate
4. Updated test code with @DataJpaTest

# For Spring Data JPA
Rewrote the DAO layer. Used Spring Data JPA instead of Hibernate.

![image-2](https://github.com/AndriiChipets/SchoolConsoleApp_ServiceLayer_ConsoleMenu_Hibernate_SpringDataJPA/assets/137887124/155396f5-0b84-4092-acc2-ee9500b5faf7)
![image-1](https://github.com/AndriiChipets/SchoolConsoleApp_ServiceLayer_ConsoleMenu_Hibernate_SpringDataJPA/assets/137887124/982bcaaf-2046-42fc-ac9b-f52e262f79ef)
![image-5](https://github.com/AndriiChipets/SchoolConsoleApp_ServiceLayer_ConsoleMenu_Hibernate_SpringDataJPA/assets/137887124/15d30644-1eab-44b2-a2c3-db963c84d405)
![image-4](https://github.com/AndriiChipets/SchoolConsoleApp_ServiceLayer_ConsoleMenu_Hibernate_SpringDataJPA/assets/137887124/9d1b7b2b-09b6-4001-9dc2-7d3f101ac456)
![image-3](https://github.com/AndriiChipets/SchoolConsoleApp_ServiceLayer_ConsoleMenu_Hibernate_SpringDataJPA/assets/137887124/83cfc4ef-793c-47fb-848d-a80edbfa6d34)

