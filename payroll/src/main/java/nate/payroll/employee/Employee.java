package nate.payroll.employee;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
@Data
public class Employee {
    private @Id @GeneratedValue Long id;
    private String firstname;
    private String lastname;
    private String role;

    Employee(){}

    public Employee(String firstname, String lastname, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.role = role;
    }
    public String getName() {
        return this.firstname + " " + this.lastname;
    }

    public void setName(String name) {
        String[] parts = name.split(" ");
        this.firstname = parts[0];
        this.lastname = parts[1];
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id) &&
                firstname.equals(employee.firstname) &&
                lastname.equals(employee.lastname) &&
                role.equals(employee.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, role);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
