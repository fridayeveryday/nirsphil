package com.example.web.repo;

import com.example.web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByLastnameAndFirstname(String lastname, String firstname);
    User findById(long id);
}