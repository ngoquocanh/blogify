package com.quopri.blogify.repository;

import com.quopri.blogify.model.Article;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    List<Article> findAllByArticleStatus(Article.Status status);
    Page<Article> findAllByArticleStatus(Article.Status status, Pageable pageable);
    Article findByArticleNameIgnoreCase(String articleName) throws DataAccessException;
}
