package ng.edu.aun.sce.repository;

import ng.edu.aun.sce.model.FacultyCourseStudent;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyCourseStudentRepository extends JpaRepository<FacultyCourseStudent, Long> {
    List<FacultyCourseStudent> findAllByStudentId(String studentId);

    List<FacultyCourseStudent> findByCourseCourseId(Long courseId);

    Optional<FacultyCourseStudent> findByFacultyCourseStudentId(Long id);

    Optional<FacultyCourseStudent> findByStudentId(String student_id);
}
