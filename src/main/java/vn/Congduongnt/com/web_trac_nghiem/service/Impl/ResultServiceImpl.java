package vn.Congduongnt.com.web_trac_nghiem.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {
    @Autowired
    ResultRepository resultRepository;

    @Override
    public List<Result> getTopFive() {
        return resultRepository.findTopFive();
    }

    @Override
    public List<Result> findByHistory(String id) {
        return resultRepository.findByHistory(id);
    }

    @Override
    public Object findSum(String id) {
        return resultRepository.findSum(id);
    }

    @Override
    public Object findAvg(String id) {
        return resultRepository.findAvg(id);
    }
}
