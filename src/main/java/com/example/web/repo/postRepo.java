package com.example.web.repo;

import com.example.web.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface postRepo extends CrudRepository<Post, Long> {
}
