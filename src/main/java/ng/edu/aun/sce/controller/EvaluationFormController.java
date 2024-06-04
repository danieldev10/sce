package ng.edu.aun.sce.controller;

import ng.edu.aun.sce.model.EvaluationForm;
import ng.edu.aun.sce.model.FacultyCourseStudent;
import ng.edu.aun.sce.service.EvaluationFormService;
import ng.edu.aun.sce.service.FacultyCourseStudentService;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EvaluationFormController {

    @Autowired
    private EvaluationFormService evaluationFormService;

    @Autowired
    private FacultyCourseStudentService facultyCourseStudentService;

    @PostMapping("/faculty/submit-evaluation")
    public String submitEvaluation(@ModelAttribute EvaluationForm evaluationForm,
            @RequestParam("courseId") Long courseId) {
        evaluationFormService.save(evaluationForm);
        Long facultyCourseStudentId = evaluationForm.getFacultyCourseStudentId();
        FacultyCourseStudent facultyCourseStudent = facultyCourseStudentService
                .findByfacultyCourseStudentId(facultyCourseStudentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid facultyCourseStudentId: " + facultyCourseStudentId));

        facultyCourseStudent.setEvalrequest(false);

        facultyCourseStudentService.save(facultyCourseStudent);
        return "redirect:/faculty/index";
    }

    @GetMapping("/admin/evaluation/details/{evaluationId}")
    public String showEvaluationDetails(@PathVariable Long evaluationId, Model model) {
        Optional<EvaluationForm> evaluationOptional = evaluationFormService.findById(evaluationId);
        if (evaluationOptional.isPresent()) {
            EvaluationForm evaluation = evaluationOptional.get();
            model.addAttribute("evaluation", evaluation);
            return "evaluation-details";
        } else {
            return "redirect:/admin/index";
        }
    }

}