package com.quopri.blogify.repository;

import com.quopri.blogify.entity.ArticleTag;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleTagRepository extends PagingAndSortingRepository<ArticleTag, Long> {

    List<ArticleTag> findByTagId(Long tagId) throws DataAccessException;
}
