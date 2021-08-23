package ru.digitalhabits.homework3.domain;

import com.google.common.base.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(nullable = false)
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Generated(GenerationTime.INSERT)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    private Integer age;
/*    @Column(name = "department_id", insert="false", update="false")
    private Integer departmentId;*/
    @ManyToOne(fetch=FetchType.LAZY)
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

    /*@Override
    public boolean equals(Object o){
        o.equals(o);
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equal(firstName, person.firstName) &&
                Objects.equal(lastName, person.lastName) &&
                Objects.equal(middleName, person.middleName) &&
                Objects.equal(age, person.age);}*/
}
