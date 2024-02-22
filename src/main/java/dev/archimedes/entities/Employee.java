package dev.archimedes.entities;

import dev.archimedes.enums.EmployeeType;
import dev.archimedes.enums.RoleType;
import dev.archimedes.utils.BasicRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends BasicRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_name", nullable = false)
    private String name;

    @Column(name = "employee_email", nullable = false)
    private String email;

    @Column(name = "employee_password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type", nullable = false)
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_role", nullable = false)
    private RoleType roleType;

    @Column(name = "employee_birthdate", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dob;

    @OneToMany
    @JoinColumn(
            name = "employee_id"
    )
    private List<Address> addresses = new ArrayList<>();

    @OneToMany
    @JoinColumn(
            name = "employee_id"
    )
    private List<Attendance> attendances = new ArrayList<>();


    public void addAddress(Address address){
        this.addresses.add(address);
    }
}
