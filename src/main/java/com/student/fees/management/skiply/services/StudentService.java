package com.student.fees.management.skiply.services;

import com.student.fees.management.skiply.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(String studentId);

    Student createStudent(Student student);

    Student updateStudent(String studentId, Student updatedStudent);

    void deleteStudent(String studentId);
}


