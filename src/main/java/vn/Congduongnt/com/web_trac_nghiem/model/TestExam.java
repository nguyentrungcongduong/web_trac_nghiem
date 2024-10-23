package vn.Congduongnt.com.web_trac_nghiem.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class TestExam {
    @Id
    private Integer id;

    private Timestamp timeStart;

    private Timestamp timeEnd;

    private double scoreSum;


    @ManyToOne(targetEntity = Exam.class)
    @JoinColumn(name = "exams_id", referencedColumnName = "id")
    private Exam exams;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User users;

    public TestExam() {
    }

    public TestExam(Integer id, Timestamp timeStart, Timestamp timeEnd, double scoreSum, Exam exams, User users) {
        this.id = id;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.scoreSum = scoreSum;
        this.exams = exams;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Timestamp timeStart) {
        this.timeStart = timeStart;
    }

    public Timestamp getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Timestamp timeEnd) {
        this.timeEnd = timeEnd;
    }

    public double getScoreSum() {
        return scoreSum;
    }

    public void setScoreSum(double scoreSum) {
        this.scoreSum = scoreSum;
    }

    public Exam getExams() {
        return exams;
    }

    public void setExams(Exam exams) {
        this.exams = exams;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }
}
