package com.quopri.blogify.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "articles_tags")
@Data
public class ArticleTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "article_id")
    private Long articleId;

    @Column(name = "tag_id")
    private Long tagId;
}
