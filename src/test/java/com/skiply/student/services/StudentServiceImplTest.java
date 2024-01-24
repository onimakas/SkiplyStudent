package com.skiply.student.services;

import com.skiply.student.entities.Student;
import com.skiply.student.exceptions.DuplicateStudentException;
import com.skiply.student.exceptions.StudentNotFoundException;
import com.skiply.student.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {
    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllStudents() {
        List<Student> students = new ArrayList<>();
        LocalDate dateOfBirth1 = LocalDate.of(1995, 5, 15);
        Student student1 = new Student("1", "John", "Doe", dateOfBirth1, "2",
                "mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");
        student1.setIsDeleted(false);
        LocalDate dateOfBirth2 = LocalDate.of(1990, 8, 16);
        Student student2 = new Student("2", "Jane", "Smith", dateOfBirth2, "3",
                "mishathepadia@gmail.com", "+971506281082", "SCHOOL10002", "Gems Modern Academy School");
        students.add(student1);
        students.add(student2);
        when(studentRepository.findAllByIsDeletedIsFalse()).thenReturn(students);
        List<Student> result = studentService.getAllStudents();
        assertEquals(2, result.size());
        verify(studentRepository, times(1)).findAllByIsDeletedIsFalse();
    }

    @Test
    public void testGetStudentById_Found() throws StudentNotFoundException {
        LocalDate dateOfBirth1 = LocalDate.of(1995, 5, 15);
        Student expectedStudent = new Student("1", "John", "Doe", dateOfBirth1, "2",
                "mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");
        String studentId = "1";
        when(studentRepository.findByStudentIdAndIsDeletedIsFalse(studentId)).thenReturn(Optional.of(expectedStudent));
        Optional<Student> result = studentService.getStudentById(studentId);
        assertTrue(result.isPresent());
        assertEquals(studentId, result.get().getStudentId());
        verify(studentRepository, times(1)).findByStudentIdAndIsDeletedIsFalse(studentId);
    }

    @Test
    public void testGetStudentById_NotFound() {
        String studentId = "123";
        when(studentRepository.findByStudentIdAndIsDeletedIsFalse(studentId)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.getStudentById(studentId));
        verify(studentRepository, times(1)).findByStudentIdAndIsDeletedIsFalse(studentId);
    }

    @Test
    public void testCreateStudent_Successful() {
        LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);
        Student student = new Student("1", "John", "Doe", dateOfBirth, "2",
                "mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");
        when(studentRepository.existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumberAndIsDeletedIsFalse(student.getStudentFirstName(),
                student.getStudentLastName(), student.getStudentDob(), student.getParentMobileNumber())).thenReturn(false);
        when(studentRepository.save(student)).thenReturn(student);
        Student createdStudent = studentService.createStudent(student);
        assertNotNull(createdStudent);
        assertEquals(student.getStudentId(), createdStudent.getStudentId());
        verify(studentRepository, times(1)).existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumberAndIsDeletedIsFalse(
                student.getStudentFirstName(), student.getStudentLastName(), student.getStudentDob(), student.getParentMobileNumber());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testCreateStudent_DuplicateStudent() {
        Student student = new Student();
        when(studentRepository.existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumberAndIsDeletedIsFalse(any(), any(), any(), any())).thenReturn(true);
        assertThrows(DuplicateStudentException.class, () -> studentService.createStudent(student));
        verify(studentRepository, times(1)).existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumberAndIsDeletedIsFalse(any(), any(), any(), any());
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    public void testUpdateStudent_Successful() {
        String studentId = "123";
        Student student = new Student();
        when(studentRepository.findByStudentIdAndIsDeletedIsFalse(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        Student result = studentService.updateStudent(studentId, student);
        assertNotNull(result);
        assertEquals(studentId, result.getStudentId());
        verify(studentRepository, times(1)).findByStudentIdAndIsDeletedIsFalse(studentId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_Successful() throws StudentNotFoundException {
        String studentId = "123";
        Student student = new Student();
        student.setStudentId(studentId);
        when(studentRepository.findByStudentIdAndIsDeletedIsFalse(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        studentService.deleteStudent(studentId);
        verify(studentRepository, times(1)).findByStudentIdAndIsDeletedIsFalse(studentId);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_StudentNotFound() {
        String studentId = "123";
        when(studentRepository.findByStudentIdAndIsDeletedIsFalse(studentId)).thenReturn(Optional.empty());
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteStudent(studentId));
        verify(studentRepository, times(1)).findByStudentIdAndIsDeletedIsFalse(studentId);
        verify(studentRepository, never()).save(any(Student.class));
    }
}
