package vn.Congduongnt.com.web_trac_nghiem.model;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;

    private int totalCorrect = 0;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "users_user",referencedColumnName = "id" )
    private User users;


    @ManyToOne(targetEntity = Exam.class)
    @JoinColumn(name = "id_exam",referencedColumnName = "id")
    private Exam questions;

    public Result() {
        super();
    }

    public Result(int id, String username, int totalCorrect, User users, Exam questions) {
        this.id = id;
        this.username = username;
        this.totalCorrect = totalCorrect;
        this.users = users;
        this.questions = questions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(int totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public User getUsers() {
        return users;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public Exam getQuestions() {
        return questions;
    }

    public void setQuestions(Exam questions) {
        this.questions = questions;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}