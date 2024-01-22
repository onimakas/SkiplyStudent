package com.skiply.student;

import com.skiply.student.services.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.skiply.student.exceptions.StudentException;
import com.skiply.student.entities.Student;
import com.skiply.student.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class SkiplyApplicationTests {

	@Mock
	private StudentRepository studentRepository;

	private StudentServiceImpl studentService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		studentService = new StudentServiceImpl(studentRepository);
	}

	@Test
	public void testGetAllStudents() {
		LocalDate dateOfBirth1 = LocalDate.of(1995, 5, 15);
		Student student1 = new Student("1", "John", "Doe", dateOfBirth1, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");

		LocalDate dateOfBirth2 = LocalDate.of(1990, 8, 16);
		Student student2 = new Student("2", "Jane", "Smith", dateOfBirth2, "3",
				"mishathepadia@gmail.com", "+971506281082", "SCHOOL10002", "Gems Modern Academy School");
		List<Student> expectedStudents = Arrays.asList(student1, student2);

		when(studentRepository.findAll()).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAllStudents();

		assertEquals(expectedStudents, actualStudents);
		verify(studentRepository, times(1)).findAll();
	}

	@Test
	public void testGetStudentById_Found() {
		LocalDate dateOfBirth1 = LocalDate.of(1995, 5, 15);
		Student expectedStudent = new Student("1", "John", "Doe", dateOfBirth1, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");

		String studentId = "1";

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(expectedStudent));

		Optional<Student> actualStudent = studentService.getStudentById(studentId);

		assertTrue(actualStudent.isPresent());
		assertEquals(expectedStudent, actualStudent.get());
		verify(studentRepository, times(1)).findById(studentId);
	}

	@Test
	public void testGetStudentById_NotFound() {
		String studentId = "11";

		when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

		assertThrows(StudentException.class, () -> studentService.getStudentById(studentId));
		verify(studentRepository, times(1)).findById(studentId);
	}

	@Test
	public void testCreateStudent_ValidData() {
		LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);
		Student student = new Student("1", "John", "Doe", dateOfBirth, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");


		when(studentRepository.existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumber(student.getStudentFirstName(),
				student.getStudentLastName(), student.getStudentDob(), student.getParentMobileNumber())).thenReturn(false);
		when(studentRepository.save(student)).thenReturn(student);

		Student createdStudent = studentService.createStudent(student);

		assertNotNull(createdStudent);
		assertEquals(student.getStudentId(), createdStudent.getStudentId());
		verify(studentRepository, times(1)).existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumber(
				student.getStudentFirstName(), student.getStudentLastName(), student.getStudentDob(), student.getParentMobileNumber());
		verify(studentRepository, times(1)).save(student);
	}

	@Test
	public void testCreateStudent_ExistingStudent() {
		LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);
		Student student = new Student("1", "John", "Doe", dateOfBirth, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");


		when(studentRepository.existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumber(student.getStudentFirstName(),
				student.getStudentLastName(), student.getStudentDob(), student.getParentMobileNumber())).thenReturn(true);

		assertThrows(StudentException.class, () -> studentService.createStudent(student));

		verify(studentRepository, times(1)).existsByStudentFirstNameAndStudentLastNameAndStudentDobAndParentMobileNumber(student.getStudentFirstName(), student.getStudentLastName(), student.getStudentDob(), student.getParentMobileNumber());
		verify(studentRepository, never()).save(any(Student.class));
	}

	@Test
	public void testUpdateStudent_ExistingId() {

		String studentId = "1";
		LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);
		when(studentRepository.existsById(studentId)).thenReturn(true);
		Student updatedStudent = new Student(studentId, "John", "Doe", dateOfBirth, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL1890", "Gems ModernAcademy School");

		when(studentRepository.save(updatedStudent)).thenReturn(updatedStudent);

		Student actualStudent = studentService.updateStudent(studentId, updatedStudent);

		assertNotNull(actualStudent);
		assertEquals(studentId, actualStudent.getStudentId());
		verify(studentRepository, times(1)).existsById(studentId);
		verify(studentRepository, times(1)).save(updatedStudent);
	}

	@Test
	public void testUpdateStudent_NotExistingId() {
		String studentId = "2";
		LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);
		Student updatedStudent = new Student(studentId, "John", "Doe", dateOfBirth, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");

		when(studentRepository.existsById(studentId)).thenReturn(false);

		assertThrows(StudentException.class, () -> studentService.updateStudent(studentId, updatedStudent));
		verify(studentRepository, times(1)).existsById(studentId);
		verify(studentRepository, never()).save(any(Student.class));
	}

	@Test
	public void testDeleteStudent_ExistingId() {
		String studentId = "1";
		LocalDate dateOfBirth = LocalDate.of(1995, 5, 15);
		Student student = new Student(studentId, "John", "Doe", dateOfBirth, "2",
				"mayankthepadia@gmail.com", "+971569028304", "SCHOOL10001", "JSSIS School");

		when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
		when(studentRepository.save(student)).thenReturn(student);

		studentService.deleteStudent(studentId);

		assertTrue(student.getIsDeleted());
		assertNotNull(student.getDeletedAt());
		verify(studentRepository, times(1)).findById(studentId);
		verify(studentRepository, times(1)).save(student);
	}

	@Test
	void testDeleteStudent_NonExistingStudentId() {
		String studentId = "999";
		when(studentRepository.findById(studentId)).thenReturn(Optional.empty());
		studentService.deleteStudent(studentId);
		verify(studentRepository, times(1)).findById(studentId);
		verify(studentRepository, times(0)).save(any());
	}


}