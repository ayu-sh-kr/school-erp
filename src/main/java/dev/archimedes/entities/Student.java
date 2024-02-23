package dev.archimedes.entities;

import dev.archimedes.enums.RoleType;
import dev.archimedes.utils.BasicRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BasicRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "student_id")
    private Integer id;

    @Column(name = "student_name", nullable = false)
    @NotNull
    private String studentName;

    @NotNull
    @Email
    @Column(name = "student_email", nullable = false)
    private String studentEmail;

    @NotNull
    @Column(name = "student_father", nullable = false)
    private String fatherName;

    @NotNull
    @Column(name = "student_mother", nullable = false)
    private String motherName;

    @Column(name = "sibling_name")
    private String siblingName;

    @NotNull
    @Column(name = "standard", nullable = false)
    private String standard;

    @NotNull
    @Column(name = "section", nullable = false)
    private String section;

    @Enumerated(EnumType.STRING)
    private RoleType roleType = RoleType.STUDENT;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(
            name = "student_id"
    )
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(
            name = "student_id"
    )
    private List<Attendance> attendances = new ArrayList<>();


    public void addAddress(Address address){
        this.addresses.add(address);
    }

    public void remove(Address address){
        this.addresses.remove(address);
    }

    public void addAttendance(Attendance attendance){
        this.attendances.add(attendance);
    }

    public void removeAttendance(Attendance attendance){
        this.attendances.remove(attendance);
    }

}
