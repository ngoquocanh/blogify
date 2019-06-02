package com.quopri.blogify.repository;

import com.quopri.blogify.entity.Tag;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

}
