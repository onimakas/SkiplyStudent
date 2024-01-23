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
	@Test
	void contextLoads() {
	}
}