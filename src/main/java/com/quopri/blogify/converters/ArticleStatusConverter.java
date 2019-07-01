package com.quopri.blogify.converters;

import com.quopri.blogify.entity.Article;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ArticleStatusConverter implements AttributeConverter<Article.Status, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Article.Status articleStatus) {
        return articleStatus.getKey();
    }

    @Override
    public Article.Status convertToEntityAttribute(Integer dbData) {
        return Article.Status.findByKey(dbData);
    }
}
