package com.keybds.springblog.model;

import com.keybds.springblog.enums.ArticleEnum;

import javax.persistence.*;

@Entity
@Table(name = "article_status")
public class ArticleStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_key")
    private Integer statusKey;

    @Column(name = "status_value")
    private String statusValue;

    public ArticleStatus() {

    }

    public ArticleStatus(ArticleEnum articleEnum) {
        this.setStatusKey(articleEnum.getKey());
        this.setStatusValue(articleEnum.getValue());
    }

    public Integer getStatusKey() {
        return statusKey;
    }

    public void setStatusKey(Integer statusKey) {
        this.statusKey = statusKey;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
    }
}
