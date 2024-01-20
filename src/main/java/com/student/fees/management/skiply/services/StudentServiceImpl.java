package com.student.fees.management.skiply.services;

import com.student.fees.management.skiply.exceptions.StudentNotFoundException;
import com.student.fees.management.skiply.models.Student;
import com.student.fees.management.skiply.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(String studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public Student createStudent(Student student) {

        // Additional business logic or validation can be added here before saving
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(String studentId, Student updatedStudent) {
        // Additional business logic or validation can be added here before updating
        if (studentRepository.existsById(studentId)) {
            updatedStudent.setStudentId(studentId); // Ensure the ID is set
            return studentRepository.save(updatedStudent);
        } else {
            throw new StudentNotFoundException("Student not found with ID: " + studentId);
        }
    }

    @Override
    public void deleteStudent(String studentId) {
        // Soft delete by marking isDeleted as true and setting deletedAt timestamp
        studentRepository.findById(studentId).ifPresent(student -> {
            student.setIsDeleted(true);
            student.setDeletedAt(LocalDateTime.now());
            studentRepository.save(student);
        });
    }
}

