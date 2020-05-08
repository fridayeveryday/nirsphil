package com.example.web.repo;

import com.example.web.models.MetaPost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MetaPostRepo extends PagingAndSortingRepository<MetaPost, Long> {
}
//public interface MetaPostRepo extends CrudRepository<MetaPost, Long> {
//}
