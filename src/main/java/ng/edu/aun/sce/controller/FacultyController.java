package ng.edu.aun.sce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ng.edu.aun.sce.model.Course;
import ng.edu.aun.sce.model.FacultyCourseStudent;
import ng.edu.aun.sce.model.Student;
import ng.edu.aun.sce.model.User;
import ng.edu.aun.sce.service.CourseService;
import ng.edu.aun.sce.service.FacultyCourseStudentService;
import ng.edu.aun.sce.service.StudentService;
import ng.edu.aun.sce.service.UserService;

@Controller
public class FacultyController {
    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private FacultyCourseStudentService facultyCourseStudentService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/faculty/index")
    public String facultyIndex(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        List<FacultyCourseStudent> facultyCourseStudents = facultyCourseStudentService.findAll();

        List<FacultyCourseStudent> facultyStudents = new ArrayList<>();

        for (FacultyCourseStudent fcs : facultyCourseStudents) {
            if (fcs.getCourse().getInstructor().getUser_id() == currentUser.getUser_id()) {
                if (fcs.isEvalrequest()) {
                    facultyStudents.add(fcs);
                }
            }
        }
        if (currentUser.isVerified()) {
            Set<Course> courses = currentUser.getCourses();

            model.addAttribute("firstName", currentUser.getFirstname());
            model.addAttribute("lastName", currentUser.getLastname());
            model.addAttribute("courses", courses);
            model.addAttribute("waitingEvaluation", facultyStudents);

            return "index";
        } else {
            return "redirect:/verify-email";
        }
    }

    @GetMapping("/faculty/courses")
    public String facultyCourses(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        if (currentUser.isVerified()) {
            Set<Course> courses = currentUser.getCourses();

            model.addAttribute("firstName", currentUser.getFirstname());
            model.addAttribute("lastName", currentUser.getLastname());
            model.addAttribute("courses", courses);

            return "faculty-classes";
        } else {
            return "redirect:/verify-email";
        }
    }

    @GetMapping("/faculty/createcourse")
    public String createCourse(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("firstName", currentUser.getFirstname());
        model.addAttribute("lastName", currentUser.getLastname());
        return "faculty-createcourse";
    }

    @PostMapping("/faculty/createcourse")
    public String addCourse(@RequestParam("courseCode") String courseCode,
            @RequestParam("courseName") String courseName,
            @RequestParam("studentIds") String studentIds,
            @AuthenticationPrincipal UserDetails userDetails) {

        User instructor = userService.findByUsername(userDetails.getUsername());
        String[] studentIdArray = studentIds.split(",");
        List<FacultyCourseStudent> facultyCourseStudents = new ArrayList<>();

        for (String studentId : studentIdArray) {
            FacultyCourseStudent facultyCourseStudent = new FacultyCourseStudent();
            facultyCourseStudent.setStudent_id(studentId.trim());
            facultyCourseStudents.add(facultyCourseStudent);
        }

        Course course = new Course(courseCode, courseName, instructor, facultyCourseStudents);
        courseService.save(course);
        facultyCourseStudentService.saveAll(facultyCourseStudents);

        return "redirect:/faculty/courses";
    }

    @GetMapping("/faculty/course/details/{id}")
    public String courseDetails(Model model, @PathVariable("id") Long courseId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Course> optionalCourse = courseService.findById(courseId);

        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("firstName", currentUser.getFirstname());
        model.addAttribute("lastName", currentUser.getLastname());

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            model.addAttribute("courseCode", course.getCourseCode());
            model.addAttribute("courseName", course.getCourseName());
            model.addAttribute("courseId", course.getCourseId());
            model.addAttribute("facultyCourseStudents", course.getFacultyCourseStudents());

            return "faculty-course-details";
        } else {
            return "error";
        }
    }

    @PostMapping("/faculty/course/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") Long courseId,
            @RequestParam("facultyCourseStudentId") Long facultyCourseStudentId) {
        facultyCourseStudentService.deleteById(facultyCourseStudentId);
        return "redirect:/faculty/course/details/" + courseId;
    }

    @PostMapping("/faculty/course/addStudentId")
    public String addStudentId(@RequestParam("courseId") Long courseId,
            @RequestParam("newStudentId") String newStudentId,
            RedirectAttributes redirectAttributes) {
        Optional<Course> optionalCourse = courseService.findById(courseId);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            List<FacultyCourseStudent> facultyCourseStudents = facultyCourseStudentService.findByCourseId(courseId);

            boolean studentExists = facultyCourseStudents.stream()
                    .anyMatch(fcs -> fcs.getStudent_id().equals(newStudentId));

            if (studentExists) {
                redirectAttributes.addFlashAttribute("warning", "Student ID already exists in the course!");
            } else {
                FacultyCourseStudent facultyCourseStudent = new FacultyCourseStudent(course, newStudentId);
                Student student = studentService.findByStudentId(newStudentId).orElse(null);
                if (student != null && student.isSendRequest()) {
                    facultyCourseStudent.setEvalrequest(true);
                }
                facultyCourseStudentService.save(facultyCourseStudent);
            }

            return "redirect:/faculty/course/details/" + courseId;
        } else {
            return "error";
        }
    }

    @GetMapping("/evaluate/{courseId}/{studentId}/{facultyCourseStudentId}")
    public String evaluateStudent(
            @PathVariable Long courseId,
            @PathVariable String studentId,
            @PathVariable Long facultyCourseStudentId,
            Model model,
            @AuthenticationPrincipal UserDetails userDetails) {

        Optional<FacultyCourseStudent> optionalFacultyCourseStudent = facultyCourseStudentService
                .findByfacultyCourseStudentId(facultyCourseStudentId);

        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("firstName", currentUser.getFirstname());
        model.addAttribute("lastName", currentUser.getLastname());

        Optional<Course> optionalCourse = courseService.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();

            Student student = studentService.findByStudentId(studentId).orElse(null);
            if (student != null) {
                model.addAttribute("instructorFirstName", course.getInstructor().getFirstname());
                model.addAttribute("instructorLastName", course.getInstructor().getLastname());
                model.addAttribute("courseCode", course.getCourseCode());
                model.addAttribute("courseId", course.getCourseId());
                model.addAttribute("studentId", studentId);
                model.addAttribute("studentFirstName", student.getFirstname());
                model.addAttribute("studentLastName", student.getLastname());
                model.addAttribute("instructorId", currentUser.getUser_id());

                if (optionalFacultyCourseStudent.isPresent()) {
                    FacultyCourseStudent facultyCourseStudent = optionalFacultyCourseStudent.get();
                    model.addAttribute("facultyCourseStudentId", facultyCourseStudent.getFacultyCourseStudentId());
                }
                return "evaluation-form";
            }
        }
        return "error";
    }

    @PostMapping("/faculty/course/details/delete/{id}")
    public String deleteCourse(@PathVariable("id") Long courseId) {
        List<FacultyCourseStudent> studentsInCourse = facultyCourseStudentService.findByCourseId(courseId);
        for (FacultyCourseStudent stc : studentsInCourse) {
            facultyCourseStudentService.deleteById(stc.getFacultyCourseStudentId());
        }
        courseService.deleteById(courseId);
        return "redirect:/faculty/index";
    }

    @GetMapping("/faculty/course/details/edit/{id}")
    public String viewEditCourseForm(@PathVariable("id") Long courseId, Model model,
            @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("firstName", currentUser.getFirstname());
        model.addAttribute("lastName", currentUser.getLastname());
        Course course = courseService.findById(courseId).orElse(null);
        model.addAttribute("course", course);
        return "faculty-edit-course";
    }

    @PostMapping("/faculty/course/details/edit/{id}")
    public String editCourse(
            @PathVariable("id") Long courseId,
            @RequestParam String courseCode,
            @RequestParam String courseName) {
        courseService.editCourse(courseId, courseCode, courseName);
        return "redirect:/faculty/course/details/" + courseId;
    }
}
