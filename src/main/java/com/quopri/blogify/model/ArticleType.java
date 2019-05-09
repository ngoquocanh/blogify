package com.quopri.blogify.model;

import com.quopri.blogify.enums.ArticleEnum;

import javax.persistence.*;

@Entity
@Table(name = "article_type")
public class ArticleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_key")
    private Integer typeKey;

    @Column(name = "type_value")
    private String typeValue;

    public ArticleType() {

    }

    public ArticleType(ArticleEnum articleEnum) {
        this.setTypeKey(articleEnum.getKey());
        this.setTypeValue(articleEnum.getValue());
    }

    public Integer getTypeKey() {
        return typeKey;
    }

    public void setTypeKey(Integer typeKey) {
        this.typeKey = typeKey;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }
}
