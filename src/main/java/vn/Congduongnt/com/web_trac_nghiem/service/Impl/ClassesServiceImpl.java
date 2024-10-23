package vn.Congduongnt.com.web_trac_nghiem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClassesServiceImpl implements ClassesService {
    @Autowired
    private ClassesRepository classesRepository;

    @Override
    public Iterable<Classes> findAll() {
        return classesRepository.findAll();
    }
}
