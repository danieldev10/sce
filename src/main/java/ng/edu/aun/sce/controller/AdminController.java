package ng.edu.aun.sce.controller;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

import ng.edu.aun.sce.model.Course;
import ng.edu.aun.sce.model.EvaluationForm;
import ng.edu.aun.sce.model.FacultyCourseStudent;
import ng.edu.aun.sce.model.Student;
import ng.edu.aun.sce.model.User;
import ng.edu.aun.sce.service.CourseService;
import ng.edu.aun.sce.service.EvaluationFormService;
import ng.edu.aun.sce.service.FacultyCourseStudentService;
import ng.edu.aun.sce.service.StudentService;
import ng.edu.aun.sce.service.UserService;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FacultyCourseStudentService facultyCourseStudentService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EvaluationFormService evaluationFormService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/admin/students")
    public String allStudents(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Student> students = studentService.findAll();
        model.addAttribute("atRiskStudents", students);
        return "admin-students";
    }

    @GetMapping("/admin/index")
    public String adminIndex(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = userService.findByUsername(userDetails.getUsername());
        List<Student> students = studentService.findAll();
        List<FacultyCourseStudent> facultyCourseStudents = facultyCourseStudentService.findAll();
        List<User> adminList = userService.findFacultyWithRoles();

        int awaitingEvaluations = 0;
        int submitedEvaluations = 0;
        int numberOfAtRiskStudents = students.size();
        double responseRate = 0;

        for (FacultyCourseStudent fcs : facultyCourseStudents) {
            if (fcs.isEvalrequest()) {
                awaitingEvaluations += 1;
            } else {
                submitedEvaluations += 1;
            }
            Student st = studentService.findByStudentId(fcs.getStudent_id()).orElse(null);
            if (st == null || !st.isSendRequest()) {
                submitedEvaluations -= 1;
            }

            responseRate = ((double) submitedEvaluations / (awaitingEvaluations + submitedEvaluations)) * 100;

        }

        if (currentUser.isVerified()) {
            model.addAttribute("firstName", currentUser.getFirstname());
            model.addAttribute("lastName", currentUser.getLastname());
            model.addAttribute("atRiskStudents", students);
            model.addAttribute("awaiting", awaitingEvaluations);
            model.addAttribute("submited", submitedEvaluations);
            model.addAttribute("atRiskNumber", numberOfAtRiskStudents);
            model.addAttribute("admins", adminList);

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.HALF_UP);

            String roundedResponseRate = df.format(responseRate);

            model.addAttribute("responseRate", roundedResponseRate);
            return "admin-index";
        } else {
            return "redirect:/verify-email";
        }
    }

    @GetMapping("/admin/addstudent")
    public String createStudent(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        return "admin-addstudent";
    }

    @PostMapping("/admin/addstudent")
    public String addStudent(@ModelAttribute("students") Student student) {
        studentService.save(student);
        return "redirect:/admin/students";
    }

    @Async
    private void sendEvaluationRequest(String FacultyEmail, String subject, String message) {
        SimpleMailMessage evaluationReq = new SimpleMailMessage();
        evaluationReq.setTo(FacultyEmail);
        evaluationReq.setSubject(subject);
        evaluationReq.setText(message);

        javaMailSender.send(evaluationReq);
    }

    @PostMapping("/admin/sendEvaluationRequest")
    public String sendEvaluationRequest(@RequestParam("studentId") String studentId, Model model,
            RedirectAttributes redirectAttributes) {
        Student student = studentService.findByStudentId(studentId).orElse(null);

        student.setSendRequest(true);
        studentService.save(student);

        List<FacultyCourseStudent> facultyCourseStudents = facultyCourseStudentService.findAllByStudentId(studentId);
        if (facultyCourseStudents.isEmpty()) {
            redirectAttributes.addFlashAttribute("warning", "Student is not registered to any class yet.");
        }

        if (student != null && facultyCourseStudents != null && !facultyCourseStudents.isEmpty()) {
            for (FacultyCourseStudent facultyCourseStudent : facultyCourseStudents) {
                facultyCourseStudent.setEvalrequest(true);
                User faculty = userService.findById(facultyCourseStudent.getCourse().getInstructor().getUser_id())
                        .orElse(null);
                // if (faculty != null) {
                // String subject = "Evaluation Request for Student: " + student.getFirstname()
                // + " "
                // + student.getLastname();
                // String message = "Dear " + faculty.getFirstname()
                // + ",\n\nYou have received an evaluation request for the student "
                // + student.getFirstname() + " " + student.getLastname()
                // + ".\nPlease take necessary actions.\n\nRegards,\nAdmin";
                // String facultyEmail = faculty.getEmail();

                // sendEvaluationRequest(facultyEmail, subject, message);
                // }
            }
            model.addAttribute("successMessage", "Evaluation requests sent successfully to faculty.");
        }
        return "redirect:/admin/students";
    }

    @GetMapping("/admin/student/{studentId}")
    public String showStudentDetailedPage(@PathVariable String studentId, Model model) {
        Optional<Student> studentOptional = studentService.findByStudentId(studentId);
        if (!studentOptional.isPresent()) {
            return "redirect:/admin/index";
        }

        List<FacultyCourseStudent> facultyCourseStudents = facultyCourseStudentService.findAllByStudentId(studentId);
        List<EvaluationForm> evaluations = evaluationFormService.findByStudentId(studentId);

        model.addAttribute("student", studentOptional.get());
        model.addAttribute("facultyCourseStudents", facultyCourseStudents);
        model.addAttribute("evaluations", evaluations);

        return "admin-detailed";
    }

    @GetMapping("/admin/faculty")
    public String showFacultyList(Model model) {
        List<User> facultyList = userService.findFacultyWithoutRoles();
        model.addAttribute("facultyList", facultyList);
        return "admin-faculty";
    }

    @GetMapping("/admin/faculty/{userId}")
    public String showFacultyDetails(@PathVariable Long userId, Model model) {
        Optional<User> facultyOptional = userService.findById(userId);
        if (facultyOptional.isPresent()) {
            User faculty = facultyOptional.get();
            List<Course> courses = courseService.findByInstructorId(userId);
            List<EvaluationForm> evaluations = evaluationFormService.findByInstructorId(userId);
            model.addAttribute("faculty", faculty);
            model.addAttribute("courses", courses);
            model.addAttribute("evaluations", evaluations);
            return "admin-faculty-details";
        } else {
            return "redirect:/admin/faculty";
        }
    }

    @GetMapping("/admin/students/evaluation/details/{id}")
    public String studentEvaluationDetail(@PathVariable Long id, Model model) {
        Optional<EvaluationForm> evaluationOptional = evaluationFormService.findById(id);
        if (evaluationOptional.isPresent()) {
            EvaluationForm evaluation = evaluationOptional.get();
            model.addAttribute("evaluation", evaluation);
            model.addAttribute("studentname", evaluation.getStudentName());
            model.addAttribute("studentid", evaluation.getStudentId());
            return "admin-student-evaluation-detailed";
        } else {
            return "redirect:/admin/evaluations";
        }
    }

    @GetMapping("/admin/evaluations")
    public String allEvaluations(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<EvaluationForm> evaluations = evaluationFormService.findAll();
        model.addAttribute("evlauations", evaluations);
        return "admin-evaluations";
    }

    @GetMapping("/admin/evaluation/detailed/{id}")
    public String evaluationDetail(@PathVariable Long id, Model model) {
        Optional<EvaluationForm> evaluationOptional = evaluationFormService.findById(id);
        if (evaluationOptional.isPresent()) {
            EvaluationForm evaluation = evaluationOptional.get();
            model.addAttribute("evaluation", evaluation);
            model.addAttribute("studentname", evaluation.getStudentName());
            model.addAttribute("studentid", evaluation.getStudentId());
            return "admin-evaluation-detail";
        } else {
            return "redirect:/admin/evaluations";
        }
    }

}
