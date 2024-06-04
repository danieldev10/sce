package ng.edu.aun.sce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ng.edu.aun.sce.model.EvaluationForm;

public interface EvaluationFormRepository extends JpaRepository<EvaluationForm, Long> {
    List<EvaluationForm> findByStudentId(String studentId);

    List<EvaluationForm> findByInstructorId(Long userId);

    Optional<EvaluationForm> findById(Long evaluationId);
}
