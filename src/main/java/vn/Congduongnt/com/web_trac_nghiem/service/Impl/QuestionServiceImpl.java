package vn.Congduongnt.com.web_trac_nghiem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public Iterable<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question findById(Integer id) {
        return questionRepository.findById(id).orElse(null);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public Page<Question> findAllByExams(int id, Pageable pageable) {
        return questionRepository.findAllByExams(id, pageable);
    }

    @Override
    public Page<Question> findAllByTitleContaining(String title, Pageable pageable) {
        return questionRepository.findAllByTitleContaining(title, pageable);
    }

    @Override
    public Page<Question> findAllBySubject(Optional<Integer> id, Pageable pageable) {
        return questionRepository.findAllBySubject(id, pageable);
    }

}
