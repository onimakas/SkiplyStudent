package com.student.fees.management.skiply.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private String studentId;

    @NotBlank(message = "First name cannot be empty")
    private String studentFirstName;

    @NotBlank(message = "Last name cannot be empty")
    private String studentLastName;

    @NotNull(message = "Date of Birth cannot be null")
    @Past(message = "Date of Birth must be in the past")
    private LocalDateTime studentDob;

    @NotBlank(message = "Grade cannot be empty")
    private String studentGrade;

    @Email(message = "Invalid parent email")
    private String parentEmail;

    @Pattern(regexp = "^\\+971\\d{9}$", message = "Invalid mobile number")
    private String parentMobileNumber;

    @NotBlank(message = "School ID cannot be empty")
    private String schoolId;

    @NotBlank(message = "School name cannot be empty")
    private String schoolName;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Boolean isDeleted = false;

    private LocalDateTime deletedAt;

    // Add other fields, constructors, getters, and setters as needed
}