package vn.Congduongnt.com.web_trac_nghiem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {
    Page<User> findAll(Pageable pageable);

    Iterable<User> findAll();

    User findById(String id);

    void save(User user);

    void delete(User user);


    Integer findByTotalUser();

    String findByNewUser();


    boolean userExists(String id);

    boolean userExistss(String email);

    String findByPass(String id);
}
