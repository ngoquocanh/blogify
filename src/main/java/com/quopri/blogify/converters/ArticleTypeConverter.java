package com.quopri.blogify.converters;

import com.quopri.blogify.entity.Article;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ArticleTypeConverter implements AttributeConverter<Article.Type, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Article.Type articleType) {
        return articleType.getKey();
    }

    @Override
    public Article.Type convertToEntityAttribute(Integer dbData) {
        return Article.Type.findByKey(dbData);
    }
}