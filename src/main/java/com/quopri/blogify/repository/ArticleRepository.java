package com.quopri.blogify.repository;

import com.quopri.blogify.entity.Article;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
    List<Article> findAllByArticleStatus(Article.Status status);
    Page<Article> findAllByArticleStatus(Article.Status status, Pageable pageable);
    Article findByArticleNameIgnoreCase(String articleName) throws DataAccessException;

    @Query(value = "select a.* from articles a inner join articles_tags arta on a.id = arta.article_id " +
            "inner join tags t on arta.tag_id = t.id and a.article_status = 1 and t.id = :tagId", nativeQuery = true)
    Page<Article> findAllByTagId(Pageable pageable, @Param("tagId") Long tagId);
}
