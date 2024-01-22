package com.skiply.student.services;

import com.skiply.student.exceptions.DuplicateStudentException;
import com.skiply.student.entities.Student;
import com.skiply.student.exceptions.StudentNotFoundException;
import com.skiply.student.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
        return Optional.ofNullable(studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found")));
    }

    @Override
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumber(student.getStudentFirstName(), student.getStudentLastName(),student.getStudentDob(), student.getParentMobileNumber())) {
            throw new DuplicateStudentException("Duplicate Student Found");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(String studentId, Student updatedStudent) {
        if (studentRepository.existsById(studentId)) {
            updatedStudent.setStudentId(studentId);
            Optional<Student> studentOptional=studentRepository.findById(studentId);
            studentOptional.ifPresent(student -> {
               updatedStudent.setCreatedAt(studentOptional.get().getCreatedAt());
            });
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
            student.setDeletedAt(Instant.now());
            studentRepository.save(student);
        });
    }

}
