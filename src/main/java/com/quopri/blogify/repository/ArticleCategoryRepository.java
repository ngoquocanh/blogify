package com.quopri.blogify.repository;

import com.quopri.blogify.model.ArticleCategory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleCategoryRepository extends PagingAndSortingRepository<ArticleCategory, Long> {

    List<ArticleCategory> findByCategoryId(Long categoryId) throws DataAccessException;
}
