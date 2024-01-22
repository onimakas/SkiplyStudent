package com.skiply.student.services;

import com.skiply.student.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllStudents();

    Optional<Student> getStudentById(String studentId);

    Student createStudent(Student student);

    Student updateStudent(String studentId, Student updatedStudent);

    void deleteStudent(String studentId);
}


