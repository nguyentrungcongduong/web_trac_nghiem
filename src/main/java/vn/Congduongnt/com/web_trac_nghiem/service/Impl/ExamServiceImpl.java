package vn.Congduongnt.com.web_trac_nghiem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.codegym.model.Exam;
import vn.codegym.repository.ExamRepository;
import vn.codegym.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Override
    public Page<Exam> findAll(Pageable pageable) {
        return examRepository.findAll(pageable);
    }

    @Override
    public Iterable<Exam> findAll() {
        return examRepository.findAll();
    }

    @Override
    public Exam findById(Integer id) {
        return examRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Exam exam) {
        examRepository.save(exam);
    }

    @Override
    public void delete(Exam exam) {
        examRepository.delete(exam);
    }

    @Override
    public Page<Exam> findAllBySubject(int id, Pageable pageable) {
        return examRepository.findAllBySubject(id, pageable);
    }

    @Override
    public Page<Exam> findAllByNameExamContaining(String name, Pageable pageable) {
        return examRepository.findAllByNameExamContaining(name, pageable);
    }

}
