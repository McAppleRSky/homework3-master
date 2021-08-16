package ru.digitalhabits.homework3.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    private Boolean closed;
    //                               for cascade delete persons from deleting Department
    @OneToMany(orphanRemoval = true//, cascade = CascadeType.REMOVE
                                                                )
    private List<Person> persons;

    public Department() {

    }
    public Department(String name) {
        this.name = name;
    }
    public static Department with(String name) {
        return new Department(name);
    }
}