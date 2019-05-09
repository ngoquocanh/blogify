package com.quopri.blogify.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "articles_tags",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "articles_categories",
            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();
}
