package com.example.web.repo;

import com.example.web.models.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface postRepo extends JpaRepository<Post, Long> {
    Iterable<Post> findByImportanceTrue();
    Iterable<Post> findByImportanceFalse(Sort sort);

}
