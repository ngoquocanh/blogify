package com.quopri.blogify.repository;

import com.quopri.blogify.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

}
