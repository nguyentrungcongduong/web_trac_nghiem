package vn.Congduongnt.com.web_trac_nghiem.service;



import java.util.List;

public interface ResultService {
    List<Result> getTopFive();

    List<Result> findByHistory(String id);

    Object findSum(String id);

    Object findAvg(String id);
}
