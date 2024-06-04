package ng.edu.aun.sce.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.sce.model.EvaluationForm;

public interface EvaluationFormService {
    void save(EvaluationForm evaluationForm);

    List<EvaluationForm> findByStudentId(String studentId);

    List<EvaluationForm> findByInstructorId(Long userId);

    Optional<EvaluationForm> findById(Long evaluationId);

    List<EvaluationForm> findAll();
}
