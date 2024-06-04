package ng.edu.aun.sce.service.implementation;

import java.util.List;
import java.util.Optional;

// import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.sce.model.FacultyCourseStudent;
import ng.edu.aun.sce.repository.FacultyCourseStudentRepository;
import ng.edu.aun.sce.service.FacultyCourseStudentService;

@Service
public class FacultyCourseStudentServiceImpl implements FacultyCourseStudentService {
    @Autowired
    private FacultyCourseStudentRepository facultyCourseStudentRepository;

    @Override
    public void deleteById(Long id) {
        facultyCourseStudentRepository.deleteById(id);
    }

    @Override
    public FacultyCourseStudent save(FacultyCourseStudent facultyCourseStudent) {
        return facultyCourseStudentRepository.save(facultyCourseStudent);
    }

    @Override
    public List<FacultyCourseStudent> saveAll(List<FacultyCourseStudent> facultyCourseStudents) {
        return facultyCourseStudentRepository.saveAll(facultyCourseStudents);
    }

    @Override
    public List<FacultyCourseStudent> findAllByStudentId(String studentId) {
        return facultyCourseStudentRepository.findAllByStudentId(studentId);
    }

    @Override
    public List<FacultyCourseStudent> findByCourseId(Long courseId) {
        return facultyCourseStudentRepository.findByCourseCourseId(courseId);
    }

    @Override
    public Optional<FacultyCourseStudent> findByfacultyCourseStudentId(Long id) {
        return facultyCourseStudentRepository.findByFacultyCourseStudentId(id);
    }

    @Override
    public Optional<FacultyCourseStudent> findByStudentId(String student_id) {
        return facultyCourseStudentRepository.findByStudentId(student_id);
    }

    @Override
    public List<FacultyCourseStudent> findAll() {
        return facultyCourseStudentRepository.findAll();
    }

}
