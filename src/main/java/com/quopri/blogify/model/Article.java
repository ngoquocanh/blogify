package com.quopri.blogify.model;

import com.quopri.blogify.converters.ArticleStatusConverter;
import com.quopri.blogify.converters.ArticleTypeConverter;
import lombok.Data;
import lombok.Getter;

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

    @Column(name = "article_type")
    @Convert(converter = ArticleTypeConverter.class)
    private Type articleType = Type.POST;

    @Column(name = "article_excerpt")
    private String articleExcerpt;

    @Column(name = "article_content")
    private String articleContent;

    @Column(name = "article_image")
    private String articleImage;

    @Column(name = "article_status")
    @Convert(converter = ArticleStatusConverter.class)
    private Status articleStatus = Status.DRAFT;

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

    @Getter
    public enum Status {
        PUBLISHED(1, "published"),
        DRAFT(2, "draft");

        private Integer key;
        private String value;

        Status(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static Status findByKey(Integer key) {
            for (Status status : Status.values()) {
                if (status.key == key) {
                    return status;
                }
            }
            return null;
        }

        public static Status findByValue(String value) {
            for (Status status : Status.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            return null;
        }
    }

    @Getter
    public enum Type {
        POST(1, "post"),
        PAGE(2, "page");

        private Integer key;
        private String value;

        Type(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static Type findByKey(Integer key) {
            for (Type type : Type.values()) {
                if (type.key == key) {
                    return type;
                }
            }
            return null;
        }

        public static Type findByValue(String value) {
            for (Type type : Type.values()) {
                if (type.value.equals(value)) {
                    return type;
                }
            }
            return null;
        }
    }
}
