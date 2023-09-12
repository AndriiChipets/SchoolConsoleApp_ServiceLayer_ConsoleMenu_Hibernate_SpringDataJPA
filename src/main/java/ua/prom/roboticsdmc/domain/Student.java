package ua.prom.roboticsdmc.domain;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("Student")
@Table(name = "users", schema = "school_app_schema")
@EqualsAndHashCode(callSuper = true)
@Getter
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor(force = true)
public class Student extends User {

    @Column(name = "group_id")
    private final Integer groupId;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "students_courses", schema = "school_app_schema",
        joinColumns = { @JoinColumn (name = "user_id") }, 
        inverseJoinColumns = { @JoinColumn(name = "course_id") }
    )
    private Set<Course> courses = new HashSet<>();

}
