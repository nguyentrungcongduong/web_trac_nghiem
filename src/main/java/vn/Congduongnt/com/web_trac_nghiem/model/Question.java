package vn.Congduongnt.com.web_trac_nghiem.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Component
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int quesId;
    private String title;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int ans;
    private int chose;

    @ManyToOne(targetEntity = Subject.class)
    @JoinColumn(name = "id_subject", referencedColumnName = "id")
    private Subject subjects;

    @OneToMany(mappedBy="questions")
    private List<Result> results;

    public Question() {
        super();
    }

    public Question(int quesId, String title, String optionA, String optionB, String optionC,String optionD ,int ans, int chose,
                    Subject subjects, List<Result> results) {
        this.quesId = quesId;
        this.title = title;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.ans = ans;
        this.chose = chose;
        this.subjects = subjects;
        this.results = results;
        this.optionD = optionD;
    }

    public int getQuesId() {
        return quesId;
    }

    public void setQuesId(int quesId) {
        this.quesId = quesId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public int getAns() {
        return ans;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getChose() {
        return chose;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public void setChose(int chose) {
        this.chose = chose;
    }

    public Subject getSubject() {
        return subjects;
    }

    public void setSubject(Subject subject) {
        this.subjects = subject;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Question{" +
                "quesId=" + quesId +
                ", title='" + title + '\'' +
                ", optionA='" + optionA + '\'' +
                ", optionB='" + optionB + '\'' +
                ", optionC='" + optionC + '\'' +
                ", ans=" + ans +
                ", chose=" + chose +
                ", subjects=" + subjects +
                ", results=" + results +
                '}';
    }
}