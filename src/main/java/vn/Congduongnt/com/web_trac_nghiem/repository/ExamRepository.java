package vn.Congduongnt.com.web_trac_nghiem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Integer> {
   @Query(value = "SELECT * FROM exam join subject where exam.subject_id = subject.id and subject.id = :id",
           nativeQuery = true)
   Page<Exam> findAllBySubject(@Param("id") int id, Pageable pageable);

   @Query(value = "select * from exam_question join question on exam_question.ques_id = question.ques_id where exam_question.exam_id = :id",
           nativeQuery = true)
   Page<Exam> findAllByQuestions(@Param("id") int id, Pageable pageable);

   Page<Exam> findAllByNameExamContaining(String name, Pageable pageable);
}
