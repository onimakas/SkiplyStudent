package com.student.fees.management.skiply.repositories;
import com.student.fees.management.skiply.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

    // You can add custom query methods here if needed

}