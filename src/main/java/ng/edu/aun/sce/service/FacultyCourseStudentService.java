package ng.edu.aun.sce.service;

import java.util.List;
import java.util.Optional;

import ng.edu.aun.sce.model.FacultyCourseStudent;

public interface FacultyCourseStudentService {
    public FacultyCourseStudent save(FacultyCourseStudent facultyCourseStudent);

    public List<FacultyCourseStudent> saveAll(List<FacultyCourseStudent> facultyCourseStudents);

    void deleteById(Long id);

    List<FacultyCourseStudent> findAll();

    List<FacultyCourseStudent> findAllByStudentId(String studentId);

    List<FacultyCourseStudent> findByCourseId(Long courseId);

    Optional<FacultyCourseStudent> findByfacultyCourseStudentId(Long id);

    Optional<FacultyCourseStudent> findByStudentId(String student_id);

    // void updateEvalRequest(String studentId, Long courseId, boolean evalRequest);
}
