package ng.edu.aun.sce.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ng.edu.aun.sce.model.Course;
import ng.edu.aun.sce.repository.CourseRepository;
import ng.edu.aun.sce.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public List<Course> findByInstructorId(Long userId) {
        return courseRepository.findByInstructorUserId(userId);
    }

    @Override
    public void editCourse(Long id, String courseCode, String courseName) {
        Course existingCourse = courseRepository.findById(id).orElse(null);

        if (existingCourse != null) {
            existingCourse.setCourseCode(courseCode);
            existingCourse.setCourseName(courseName);
        }

        courseRepository.save(existingCourse);
    }

}
