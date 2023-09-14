package ua.prom.roboticsdmc.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "groups", schema = "school_app_schema")
@EqualsAndHashCode
@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@ToString
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private final Integer groupId;

    @Column(name = "group_name", length = 40, nullable = false, unique = true)
    @Convert(converter = StringTrimConverter.class)
    private final String groupName;

}
