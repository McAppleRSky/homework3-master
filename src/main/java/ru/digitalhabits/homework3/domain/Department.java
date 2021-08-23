package ru.digitalhabits.homework3.domain;

import com.google.common.base.Objects;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
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

    /*@Column(
            nullable = false
            , columnDefinition = "boolean default false")*/
    private Boolean closed = false;
    //                               for cascade delete persons from deleting Department
    @OneToMany(orphanRemoval = true//, cascade = CascadeType.REMOVE
                                                                )
    private List<Person> persons;

    public Department() {

    }
    /*public Department(String name) {
        this.name = name;
    }*/
    public static Department with(String name) {
        return new Department().setName(name);
    }

    /*@Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department department = (Department) o;
        return Objects.equal(name, department.name) &&
                Objects.equal(closed, department.closed);}*/
}