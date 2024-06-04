package ng.edu.aun.sce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.edu.aun.sce.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAll();

    Student findByFirstnameAndLastname(String firstname, String lastname);

    void deleteById(Long id);

    Optional<Student> findById(Long id);

    Optional<Student> findByStudentId(String studentId);
}
