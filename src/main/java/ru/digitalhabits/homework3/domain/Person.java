package ru.digitalhabits.homework3.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    private Integer age;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    public Person(int id){
        this.id = id;
    }

    public Person(Department department, String firstName, String lastName, String middleName, Integer age) {
        this.department = department;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.age = age;
    }

    public static Person with(int id){
        return new Person(id);
    }
    public static Person with(Department department,
                              String firstName,
                              String lastName,
                              String middleName,
                              Integer age){
        return new Person(department, firstName, lastName, middleName, age);
    }
}
