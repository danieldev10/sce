package ng.edu.aun.sce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sid;
    private String studentId;
    private String firstname;
    private String lastname;
    @Column(nullable = false)
    private boolean sendRequest;

    public Student() {
    }

    public Student(String studentId, String firstname, String lastname, boolean sendRequest) {
        this.studentId = studentId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.sendRequest = sendRequest;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getStudent_id() {
        return studentId;
    }

    public void setStudent_id(String student_id) {
        this.studentId = student_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isSendRequest() {
        return sendRequest;
    }

    public void setSendRequest(boolean sendRequest) {
        this.sendRequest = sendRequest;
    }

    @Override
    public String toString() {
        return "Student [sid=" + sid + ", student_id=" + studentId + ", firstname=" + firstname + ", lastname="
                + lastname + ", sendRequest=" + sendRequest + "]";
    }

}
