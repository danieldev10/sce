package ng.edu.aun.sce.model;

import javax.persistence.*;

@Entity
public class EvaluationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long evaluation_id;

    private String instructorName;
    private Long instructorId;
    private String courseCode;
    private Long facultyCourseStudentId;
    private String studentId;
    private Long courseId;
    private String studentName;
    private String currentSemester;
    private String currentYear;
    private String attendance;
    private String generalParticipation;
    private String generalAttitude;
    private String preparedness;
    private String leavingClass;
    private String participation;
    private String willingnessToLearn;
    private String currentGrade;
    private String attention;
    private String comment;

    public EvaluationForm() {
    }

    public EvaluationForm(String instructorName, Long instructorId, String courseCode, Long facultyCourseStudentId,
            String studentId,
            Long courseId,
            String studentName,
            String currentSemester, String currentYear, String attendance, String generalParticipation,
            String generalAttitude, String preparedness, String leavingClass, String participation,
            String willingnessToLearn, String currentGrade, String attention, String comment) {
        this.instructorName = instructorName;
        this.instructorId = instructorId;
        this.courseCode = courseCode;
        this.facultyCourseStudentId = facultyCourseStudentId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.studentName = studentName;
        this.currentSemester = currentSemester;
        this.currentYear = currentYear;
        this.attendance = attendance;
        this.generalParticipation = generalParticipation;
        this.generalAttitude = generalAttitude;
        this.preparedness = preparedness;
        this.leavingClass = leavingClass;
        this.participation = participation;
        this.willingnessToLearn = willingnessToLearn;
        this.currentGrade = currentGrade;
        this.attention = attention;
        this.comment = comment;
    }

    public Long getEvaluation_id() {
        return evaluation_id;
    }

    public void setEvaluation_id(Long evaluation_id) {
        this.evaluation_id = evaluation_id;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCurrentSemester() {
        return currentSemester;
    }

    public void setCurrentSemester(String currentSemester) {
        this.currentSemester = currentSemester;
    }

    public String getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(String currentYear) {
        this.currentYear = currentYear;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getGeneralParticipation() {
        return generalParticipation;
    }

    public void setGeneralParticipation(String generalParticipation) {
        this.generalParticipation = generalParticipation;
    }

    public String getGeneralAttitude() {
        return generalAttitude;
    }

    public void setGeneralAttitude(String generalAttitude) {
        this.generalAttitude = generalAttitude;
    }

    public String getPreparedness() {
        return preparedness;
    }

    public void setPreparedness(String preparedness) {
        this.preparedness = preparedness;
    }

    public String getLeavingClass() {
        return leavingClass;
    }

    public void setLeavingClass(String leavingClass) {
        this.leavingClass = leavingClass;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }

    public String getWillingnessToLearn() {
        return willingnessToLearn;
    }

    public void setWillingnessToLearn(String willingnessToLearn) {
        this.willingnessToLearn = willingnessToLearn;
    }

    public String getCurrentGrade() {
        return currentGrade;
    }

    public void setCurrentGrade(String currentGrade) {
        this.currentGrade = currentGrade;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getFacultyCourseStudentId() {
        return facultyCourseStudentId;
    }

    public void setFacultyCourseStudentId(Long facultyCourseStudentId) {
        this.facultyCourseStudentId = facultyCourseStudentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    @Override
    public String toString() {
        return "EvaluationForm [evaluation_id=" + evaluation_id + ", instructorName=" + instructorName
                + ", instructorId=" + instructorId + ", courseCode=" + courseCode + ", facultyCourseStudentId="
                + facultyCourseStudentId + ", studentId=" + studentId + ", courseId=" + courseId + ", studentName="
                + studentName + ", currentSemester=" + currentSemester + ", currentYear=" + currentYear
                + ", attendance=" + attendance + ", generalParticipation=" + generalParticipation + ", generalAttitude="
                + generalAttitude + ", preparedness=" + preparedness + ", leavingClass=" + leavingClass
                + ", participation=" + participation + ", willingnessToLearn=" + willingnessToLearn + ", currentGrade="
                + currentGrade + ", attention=" + attention + ", comment=" + comment + "]";
    }

}
