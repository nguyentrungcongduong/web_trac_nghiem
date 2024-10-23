package vn.Congduongnt.com.web_trac_nghiem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;



public interface ExamService {
    Page<Exam> findAll(Pageable pageable);

    Iterable<Exam> findAll();

    Exam findById(Integer id);

    void save(Exam exam);

    void delete(Exam exam);

    Page<Exam> findAllBySubject(int id, Pageable pageable);

    Page<Exam> findAllByNameExamContaining(String name, Pageable pageable);

}
