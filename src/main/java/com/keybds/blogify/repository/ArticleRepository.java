package com.keybds.blogify.repository;

import com.keybds.blogify.model.Article;
import com.keybds.blogify.model.ArticleStatus;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
//    List<Article> findByArticleStatusEquals(Integer articleStatus);
//    Page<Article> findByArticleStatusEquals(Integer articleStatus, Pageable pageable);

    List<Article> findByArticleStatusEquals(ArticleStatus articleStatus);
    Page<Article> findByArticleStatusEquals(ArticleStatus articleStatus, Pageable pageable);

    Article findByArticleNameIgnoreCase(String articleName) throws DataAccessException;
    Article findByArticleLinkIgnoreCase(String articleName);
}
