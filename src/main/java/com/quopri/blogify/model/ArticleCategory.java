package com.quopri.blogify.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "articles_categories")
@Data
public class ArticleCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "category_id")
    private Long categoryId;
}
