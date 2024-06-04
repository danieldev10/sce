package ng.edu.aun.sce.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseCode;

    private String courseName;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @OneToMany(mappedBy = "course")
    private List<FacultyCourseStudent> facultyCourseStudents;

    public Course() {
    }

    public Course(String courseCode, String courseName, User instructor,
            List<FacultyCourseStudent> facultyCourseStudents) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.instructor = instructor;
        for (FacultyCourseStudent fcs : facultyCourseStudents) {
            fcs.setCourse(this);
        }
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public List<FacultyCourseStudent> getFacultyCourseStudents() {
        return facultyCourseStudents;
    }

    public void setFacultyCourseStudents(List<FacultyCourseStudent> facultyCourseStudents) {
        this.facultyCourseStudents = facultyCourseStudents;
    }

    @Override
    public String toString() {
        return "Course [courseId=" + courseId + ", courseCode=" + courseCode + ", courseName=" + courseName
                + ", instructor=" + instructor + "]";
    }

}
