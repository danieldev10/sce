package ng.edu.aun.sce.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.sce.model.EvaluationForm;
import ng.edu.aun.sce.repository.EvaluationFormRepository;
import ng.edu.aun.sce.service.EvaluationFormService;

@Service
public class EvaluationFormServiceImpl implements EvaluationFormService {
    @Autowired
    private EvaluationFormRepository evaluationFormRepository;

    @Override
    public void save(EvaluationForm evaluationForm) {
        evaluationFormRepository.save(evaluationForm);
    }

    @Override
    public List<EvaluationForm> findByStudentId(String studentId) {
        return evaluationFormRepository.findByStudentId(studentId);
    }

    @Override
    public List<EvaluationForm> findByInstructorId(Long userId) {
        return evaluationFormRepository.findByInstructorId(userId);
    }

    @Override
    public Optional<EvaluationForm> findById(Long evaluationId) {
        return evaluationFormRepository.findById(evaluationId);
    }

    @Override
    public List<EvaluationForm> findAll() {
        return evaluationFormRepository.findAll();
    }

}
