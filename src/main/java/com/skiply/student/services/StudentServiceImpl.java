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
/**
 * The implementation of the StudentService interface.
 * Provides methods to manage student data.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    /**
     * Constructor to inject the StudentRepository dependency.
     *
     * @param studentRepository The StudentRepository instance.
     */
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Retrieves all students from the database that are not marked as deleted.
     *
     * @return The list of students.
     */
    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAllByDeletedAtIsNull();
    }

    /**
     * Retrieves a student by their ID.
     *
     * @param studentId The ID of the student to retrieve.
     * @return The optional student.
     * @throws StudentNotFoundException if the student with the specified ID is not found.
     */
    @Override
    public Optional<Student> getStudentById(String studentId) {
        return Optional.ofNullable(studentRepository.findByStudentIdAndDeletedAtIsNull(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with StudentId :"+studentId)));
    }

    /**
     * Creates a new student.
     *
     * @param student The student to create.
     * @return The created student.
     * @throws DuplicateStudentException if a duplicate student is found.
     */
    @Override
    public Student createStudent(Student student) {
        if (studentRepository.existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumberAndDeletedAtIsNull(student.getStudentFirstName(), student.getStudentLastName(),student.getStudentDob(), student.getParentMobileNumber())) {
            throw new DuplicateStudentException("Duplicate Student Found with same first name,last name, dob and parent mobile number");
        }
        student.setDeletedAt(null);
        return studentRepository.save(student);
    }

    /**
     * Updates an existing student.
     *
     * @param studentId      The ID of the student to update.
     * @param updatedStudent The updated student data.
     * @return The updated student.
     * @throws StudentNotFoundException if the student with the specified ID is not found.
     */
    @Override
    public Student updateStudent(String studentId, Student updatedStudent) {

        Student student = studentRepository.findByStudentIdAndDeletedAtIsNull(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
        if(student!=null){
            updatedStudent.setStudentId(studentId);
            updatedStudent.setCreatedAt(student.getCreatedAt());
            updatedStudent.setUpdatedAt(Instant.now());
            updatedStudent.setIsDeleted(false);
        }
        return studentRepository.save(updatedStudent);
    }

    /**
    Deletes a student by marking it as deleted.
    Soft delete is performed by setting the 'isDeleted' flag to true and updating the 'deletedAt' timestamp.
    If the student with the specified ID is not found or already marked as deleted, an exception is thrown.
    @param studentId The ID of the student to delete.
     @throws StudentNotFoundException if the student with the specified ID is not found. */

    @Override
    public void deleteStudent(String studentId) {
        // Soft delete by marking isDeleted as true and setting deletedAt timestamp
        Student student = studentRepository.findByStudentIdAndDeletedAtIsNull(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID: " + studentId));
            student.setIsDeleted(true);
            student.setDeletedAt(Instant.now());
            studentRepository.save(student);
    }

}