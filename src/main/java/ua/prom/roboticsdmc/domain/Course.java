package ua.prom.roboticsdmc.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ua.prom.roboticsdmc.converter.StringTrimConverter;

@Entity
@Table(name = "courses", schema = "school_app_schema")
@EqualsAndHashCode(exclude = { "students" })
@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private final Integer courseId;

    @Column(name = "course_name", length = 30, nullable = false, unique = true)
    @Convert(converter = StringTrimConverter.class)
    private final String courseName;

    @Column(name = "course_description", length = 255, unique = false)
    @Convert(converter = StringTrimConverter.class)
    private final String courseDescription;

    @ToString.Exclude
    @Builder.Default
    @ManyToMany(mappedBy = "courses", fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

}
