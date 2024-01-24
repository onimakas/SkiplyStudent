package com.skiply.student.repositories;
import com.skiply.student.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumberAndIsDeletedIsFalse(String firstName, String lastName, LocalDate dob, String mobileNumber);
    List<Student> findAllByIsDeletedIsFalse();
    Optional<Student> findByStudentIdAndIsDeletedIsFalse(String studentId);
}