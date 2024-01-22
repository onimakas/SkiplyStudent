package com.skiply.student.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Table(name="students")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String studentId;

    @NotBlank(message = "First name cannot be empty")
    private String studentFirstName;

    @NotBlank(message = "Last name cannot be empty")
    private String studentLastName;

    private LocalDate studentDob;

    @NotBlank(message = "Grade cannot be empty")

    @Pattern(regexp = "^(?:PREKG|FS[12]|[1-9]|1[0-2]|YEAR [1-9]|YEAR 1[0-3])(?: [A-Z])?$", message = "Invalid grade")
    private String studentGrade;

    @Email(message = "Invalid parent email")
    private String parentEmail;

    @Pattern(regexp = "^\\+\\d{12}$", message = "Invalid mobile number")
    private String parentMobileNumber;

    @NotBlank(message = "School ID cannot be empty")
    private String schoolId;

    @NotBlank(message = "School name cannot be empty")
    private String schoolName;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private Boolean isDeleted = false;

    private Instant deletedAt;

    public Student(String studentId, String studentFirstName, String studentLastName, LocalDate studentDob, String studentGrade,
                   String parentEmail, String parentMobileNumber, String schoolId, String schoolName) {
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.studentDob = studentDob;
        this.studentGrade = studentGrade;
        this.parentEmail = parentEmail;
        this.parentMobileNumber = parentMobileNumber;
        this.schoolId = schoolId;
        this.schoolName = schoolName;
        this.studentId = studentId;
    }
}