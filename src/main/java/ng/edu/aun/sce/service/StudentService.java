package ng.edu.aun.sce.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.sce.model.Student;

public interface StudentService {
    public void save(Student student);

    List<Student> findAll();

    Student findByFirstnameAndLastname(String firstname, String lastname);

    void deleteById(Long id);

    Optional<Student> findById(Long id);

    Optional<Student> findByStudentId(String studentId);
}
