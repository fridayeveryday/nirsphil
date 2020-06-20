package com.example.web.repo;

import com.example.web.models.Action;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface actionRepo extends PagingAndSortingRepository<Action,Long> {
}
