package com.quopri.blogify.repository;

import com.quopri.blogify.entity.ArticleTag;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleTagRepository extends PagingAndSortingRepository<ArticleTag, Long> {

    List<ArticleTag> findByTagId(Long tagId) throws DataAccessException;

    @Modifying
    @Query(value = "insert into articles_tags (tag_id, article_id) values (:tagId, :articleId)", nativeQuery = true)
    void insert(@Param("tagId") Long tagId, @Param("articleId") Long articleId);
}
