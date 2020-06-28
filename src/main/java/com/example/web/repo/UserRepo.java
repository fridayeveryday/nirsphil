package com.example.web.repo;

import com.example.web.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    User findByEmail(String email);
    User findByLastnameAndFirstname(String lastname, String firstname);
    User findById(long id);
    List<User> findByFirstnameContainingAndLastnameContaining(String firstname, String lastname);
}