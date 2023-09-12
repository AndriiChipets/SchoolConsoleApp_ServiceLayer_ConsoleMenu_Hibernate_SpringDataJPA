package ua.prom.roboticsdmc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ua.prom.roboticsdmc.converter.StringTrimConverter;

@Entity
@Table(name = "users", schema = "school_app_schema")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@EqualsAndHashCode
@Getter
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor(force = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private final Integer userId;

    @Column(name = "first_name", length = 30, nullable = false, unique = false)
    @Convert(converter = StringTrimConverter.class)
    private final String firstName;

    @Column(name = "last_name", length = 30, nullable = false, unique = false)
    @Convert(converter = StringTrimConverter.class)
    private final String lastName;

    @Column(name = "email", length = 50, unique = true)
    @Convert(converter = StringTrimConverter.class)
    private final String email;

    @Column(name = "password", length = 50, unique = false)
    @Convert(converter = StringTrimConverter.class)
    private final String password;

}
