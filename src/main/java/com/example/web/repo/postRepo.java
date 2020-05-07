package com.example.web.repo;

import com.example.web.models.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface postRepo extends PagingAndSortingRepository<Post, Long> {
}
