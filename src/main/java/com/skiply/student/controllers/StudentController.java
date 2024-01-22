package com.skiply.student.controllers;

import com.skiply.student.exceptions.ErrorDetails;
import com.skiply.student.exceptions.StudentException;
import com.skiply.student.exceptions.StudentNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.skiply.student.services.StudentService;
import com.skiply.student.entities.Student;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (StudentException e) {
            ErrorDetails error = new ErrorDetails(LocalDateTime.now(), "Failed to retrieve students", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@PathVariable String studentId) {
        try {
            Optional<Student> student = studentService.getStudentById(studentId);
            return ResponseEntity.ok(student);
        } catch (StudentNotFoundException e) {
            ErrorDetails error = new ErrorDetails(LocalDateTime.now(), "Failed to retrieve student with Id: "+studentId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody @Valid Student student, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
                throw new StudentException(getValidationErrors(bindingResult));
            }
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (StudentException e) {
            ErrorDetails error = new ErrorDetails(LocalDateTime.now(), "Failed to create student", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PatchMapping("/{studentId}")
    public ResponseEntity<?> updateStudentById(@PathVariable String studentId, @RequestBody Student updatedStudent) {
        try {
            Student student = studentService.updateStudent(studentId, updatedStudent);
            return ResponseEntity.ok(student);
        } catch (StudentException e) {
            ErrorDetails error = new ErrorDetails(LocalDateTime.now(), "Failed to update student with Id: "+studentId, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudentById(@PathVariable String studentId) {
        try {
            studentService.deleteStudent(studentId);
            return ResponseEntity.ok().build();
        } catch (StudentException e) {
            ErrorDetails error = new ErrorDetails(LocalDateTime.now(), "Failed  to delete student", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    private String getValidationErrors(BindingResult bindingResult) {
        StringBuilder errors = new StringBuilder();
        bindingResult.getAllErrors().forEach(error -> {
            errors.append(error.getDefaultMessage()).append("\n");
        });
        return errors.toString().trim();
    }
}

