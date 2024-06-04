package ng.edu.aun.sce.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.sce.model.Course;

public interface CourseService {
    public void save(Course course);

    List<Course> findAll();

    void deleteById(Long id);

    Optional<Course> findById(Long id);

    List<Course> findByInstructorId(Long userId);

    void editCourse(Long id, String courseCode, String courseName);
}
