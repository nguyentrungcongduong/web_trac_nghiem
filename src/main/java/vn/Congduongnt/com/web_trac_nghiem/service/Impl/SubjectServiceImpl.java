package vn.Congduongnt.com.web_trac_nghiem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public Iterable<Subject> findAll() {
        return subjectRepository.findAll();
    }
}
