package com.keybds.blogify.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "articles")
@Data
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "article_title")
    private String articleTitle;

    @Column(name = "article_name")
    private String articleName;

    @Column(name = "article_link")
    private String articleLink;

    @Column(name = "article_date")
    private ZonedDateTime articleDate;

    @Column(name = "article_modified")
    private ZonedDateTime articleModified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_type_key", referencedColumnName = "type_key")
    private ArticleType articleType;

    @Column(name = "article_excerpt")
    private String articleExcerpt;

    @Column(name = "article_content")
    private String articleContent;

    @Column(name = "article_image")
    private String articleImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_status_key", referencedColumnName = "status_key")
    private ArticleStatus articleStatus;
}
