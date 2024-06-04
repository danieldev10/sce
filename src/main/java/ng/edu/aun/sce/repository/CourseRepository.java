package ng.edu.aun.sce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ng.edu.aun.sce.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAll();

    void deleteById(Long id);

    Optional<Course> findById(Long id);

    @Query("SELECT c FROM Course c WHERE c.instructor.user_id = :userId")
    List<Course> findByInstructorUserId(@Param("userId") Long userId);
}
