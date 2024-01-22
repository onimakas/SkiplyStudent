package com.skiply.student.repositories;
import com.skiply.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDate;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    boolean existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumber(String firstName, String lastName, LocalDate dob, String mobileNumber);

}