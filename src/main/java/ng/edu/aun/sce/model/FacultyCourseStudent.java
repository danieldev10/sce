package ng.edu.aun.sce.model;

import javax.persistence.*;

@Entity
public class FacultyCourseStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyCourseStudentId;

    private String studentId;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private boolean evalrequest;

    public FacultyCourseStudent() {
    }

    public FacultyCourseStudent(Course course, String studentId) {
        this.course = course;
        this.studentId = studentId;
    }

    public Long getFacultyCourseStudentId() {
        return facultyCourseStudentId;
    }

    public void setFacultyCourseStudentId(Long facultyCourseStudentId) {
        this.facultyCourseStudentId = facultyCourseStudentId;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStudent_id() {
        return studentId;
    }

    public void setStudent_id(String student_id) {
        this.studentId = student_id;
    }

    public boolean isEvalrequest() {
        return evalrequest;
    }

    public void setEvalrequest(boolean evalrequest) {
        this.evalrequest = evalrequest;
    }

    @Override
    public String toString() {
        return "FacultyCourseStudent [facultyCourseStudentId=" + facultyCourseStudentId + ", studentId=" + studentId
                + ", evalrequest=" + evalrequest + "]";
    }

}
