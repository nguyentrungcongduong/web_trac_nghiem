package vn.Congduongnt.com.web_trac_nghiem.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private  String nameClass;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL)
    private Set<Exam> exams;

    public Classes() {
    }

    public Classes(Integer id, String nameClass, Set<Exam> exams) {
        this.id = id;
        this.nameClass = nameClass;
        this.exams = exams;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameClass() {
        return nameClass;
    }

    public void setNameClass(String nameClass) {
        this.nameClass = nameClass;
    }

    public Set<Exam> getExams() {
        return exams;
    }

    public void setExams(Set<Exam> exams) {
        this.exams = exams;
    }
}
